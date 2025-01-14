package source.code.oneclickbooking.service.implementation.booking

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.booking.BookingCreateDto
import source.code.oneclickbooking.dto.request.booking.BookingUpdateDto
import source.code.oneclickbooking.dto.request.booking.BookingUpdateStatusDto
import source.code.oneclickbooking.dto.response.booking.BookingDetailedResponseDto
import source.code.oneclickbooking.dto.response.booking.BookingResponseDto
import source.code.oneclickbooking.exception.LocalizedIllegalArgument
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.utils.AuthorizationUtil
import source.code.oneclickbooking.utils.ExceptionMessages
import source.code.oneclickbooking.mapper.BookingMapper
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.repository.EmployeeRepository
import source.code.oneclickbooking.repository.ServicePointRepository
import source.code.oneclickbooking.repository.TreatmentRepository
import source.code.oneclickbooking.service.declaration.booking.BookingService
import source.code.oneclickbooking.service.declaration.schedule.ScheduleUtilsService
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import source.code.oneclickbooking.service.declaration.util.ValidationService
import source.code.oneclickbooking.specification.BookingSpecification
import source.code.oneclickbooking.specification.SpecificationBuilder
import source.code.oneclickbooking.specification.SpecificationFactory
import java.time.LocalDateTime

@Service
class BookingServiceImpl(
    private val validationService: ValidationService,
    private val jsonPatchService: JsonPatchService,
    private val scheduleUtilsService: ScheduleUtilsService,
    private val mapper: BookingMapper,
    private val repository: BookingRepository,
    private val servicePointRepository: ServicePointRepository,
    private val employeeRepository: EmployeeRepository,
    private val treatmentRepository: TreatmentRepository,
    private val bookingRepository: BookingRepository,
): BookingService {
    @Transactional
    override fun create(bookingDto: BookingCreateDto): BookingResponseDto {
        validateBookingCreateDto(bookingDto)
        val userId = AuthorizationUtil.getUserId();

        val booking = mapper.toEntity(bookingDto, userId)

        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    @Transactional
    override fun update(id: Int, patch: JsonMergePatch): BookingResponseDto {
        val booking = find(id)
        validateBookingStatus(booking)

        val patched = applyPatch(patch)

        validationService.validate(patched)
        validateBookingUpdateDto(booking, patched)

        mapper.update(booking, patched)
        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    @Transactional
    override fun delete(id: Int) {
        val booking = find(id)
        repository.delete(booking)
    }

    @Transactional
    override fun updateStatus(id: Int, statusDto: BookingUpdateStatusDto): BookingResponseDto {
        val booking = find(id)
        booking.status = statusDto.status
        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    override fun get(id: Int): BookingResponseDto {
        return find(id).let { mapper.toResponseDto(it) }
    }

    override fun getAll(): List<BookingResponseDto> {
        return repository.findAll().map { mapper.toResponseDto(it) }
    }

    override fun getFiltered(filter: FilterDto): List<BookingDetailedResponseDto> {
        val bookingFactory = SpecificationFactory { BookingSpecification(it) }
        val specificationBuilder = SpecificationBuilder(filter, bookingFactory)
        val specification = specificationBuilder.build()
        return repository.findAll(specification).map { mapper.toDetailedResponseDto(it) }
    }

    private fun applyPatch(patch: JsonMergePatch): BookingUpdateDto {
        return jsonPatchService.applyPatch(patch, BookingUpdateDto(), BookingUpdateDto::class)
    }

    private fun validateBookingCreateDto(bookingDto: BookingCreateDto) {
        val servicePoint = findServicePoint(bookingDto.servicePointId)
        val employee = findEmployee(bookingDto.employeeId)
        val treatment = findTreatment(bookingDto.treatmentId)

        validateAssociations(employee, treatment, servicePoint)
        validateAvailability(bookingDto.date, servicePoint, employee, treatment)
    }

    private fun validateBookingUpdateDto(existingBooking: Booking, updateDto: BookingUpdateDto) {
        val date = updateDto.date ?: existingBooking.date
        val servicePointId = updateDto.servicePointId ?: existingBooking.servicePoint.id!!
        val employeeId = updateDto.employeeId ?: existingBooking.employee!!.id!!
        val treatmentId = updateDto.treatmentId ?: existingBooking.treatment!!.id!!

        val servicePoint = findServicePoint(servicePointId)
        val employee = findEmployee(employeeId)
        val treatment = findTreatment(treatmentId)

        validateAssociations(employee, treatment, servicePoint)
        validateAvailability(date, servicePoint, employee, treatment)
    }

    private fun validateBookingStatus(booking: Booking) {
        if (booking.status != BookingStatus.PENDING) {
            throw LocalizedIllegalArgument(ExceptionMessages.BOOKING_NOT_PENDING)
        }
    }

    private fun validateAssociations(
        employee: Employee,
        treatment: Treatment,
        servicePoint: ServicePoint
    ) {
        if(!employee.treatments.contains(treatment)) {
            throw LocalizedIllegalArgument(ExceptionMessages.EMPLOYEE_NOT_PROVIDE_TREATMENT)
        }

        if(!employee.servicePointAssociations.any { it.servicePoint == servicePoint }) {
            throw LocalizedIllegalArgument(ExceptionMessages.EMPLOYEE_NOT_ASSOCIATED_WITH_SERVICE_POINT)
        }
    }

    private fun validateAvailability(
        date: LocalDateTime,
        servicePoint: ServicePoint,
        employee: Employee,
        treatment: Treatment
    ) {
        val availabilities = employee.availabilities.filter { it.dayOfWeek == date.dayOfWeek }

        if(availabilities.isEmpty()) {
            throw LocalizedIllegalArgument(
                ExceptionMessages.EMPLOYEE_HAS_NO_AVAILABILITIES_ON_DATE
            )
        }

        val bookings = bookingRepository.findByServicePointIdAndEmployeeIdAndDate(
            servicePoint.id!!,
            employee.id!!,
            date
        )

        val allPotentialSlots = availabilities
            .flatMap {
                scheduleUtilsService.generatePotentialSlots(
                    date.toLocalDate(),
                    it,
                    SLOT_INCREMENT_MINUTES,
                    treatment.duration
                )
            }.distinct()
        println("ALL POTENTIAL SLOTS: $allPotentialSlots")

        val takenSLots = scheduleUtilsService.findTakenSlots(
            allPotentialSlots,
            bookings,
            treatment.duration
        )

        val freeSlots = allPotentialSlots.filterNot { it in takenSLots }

        println("ALL FREE SLOTS: $freeSlots")
        if(date !in freeSlots) {
            throw LocalizedIllegalArgument(
                ExceptionMessages.EMPLOYEE_HAS_NO_AVAILABILITIES_ON_TIME
            )
        }
    }

    private fun find(id: Int): Booking {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException(Booking::class, id)
        }
    }

    private fun findTreatment(id: Int): Treatment {
        return treatmentRepository.findById(id).orElseThrow {
            RecordNotFoundException(Treatment::class, id)
        }
    }

    private fun findEmployee(id: Int): Employee {
        return employeeRepository.findByIdWithAssociations(id)
            ?: throw RecordNotFoundException(Employee::class, id)
    }

    private fun findServicePoint(id: Int): ServicePoint {
        return servicePointRepository.findById(id).orElseThrow {
            RecordNotFoundException(ServicePoint::class, id)
        }
    }

    companion object {
        const val SLOT_INCREMENT_MINUTES = 15
    }
}