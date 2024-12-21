package source.code.oneclickbooking.service.implementation.booking

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.BookingMapper
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.*
import source.code.oneclickbooking.service.declaration.booking.BookingService
import source.code.oneclickbooking.service.declaration.schedule.ScheduleUtilsService
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import source.code.oneclickbooking.service.declaration.util.ValidationService
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
        validateBookingRequest(bookingDto)

        val booking = mapper.toEntity(bookingDto)
        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    @Transactional
    override fun update(id: Int, patch: JsonMergePatch): BookingResponseDto {
        val booking = find(id)
        val patched = applyPatch(booking, patch)

        validationService.validate(patched)
        mapper.update(booking, patched)

        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    @Transactional
    override fun delete(id: Int) {
        val booking = find(id)
        repository.delete(booking)
    }

    override fun get(id: Int): BookingResponseDto {
        return find(id).let { mapper.toResponseDto(it) }
    }

    override fun getAll(): List<BookingResponseDto> {
        return repository.findAll().map { mapper.toResponseDto(it) }
    }

    private fun applyPatch(booking: Booking, patch: JsonMergePatch): BookingUpdateDto {
        val responseDto = mapper.toResponseDto(booking)
        return jsonPatchService.applyPatch(patch, responseDto, BookingUpdateDto::class)
    }

    private fun validateBookingRequest(bookingDto: BookingCreateDto) {
        val servicePoint = findServicePoint(bookingDto.servicePointId)
        val employee = findEmployee(bookingDto.employeeId)
        val treatment = findTreatment(bookingDto.treatmentId)

        if(!employee.treatments.contains(treatment)
            || !employee.servicePointAssociations.any { it.servicePoint == servicePoint }) {
            throw IllegalArgumentException("Employee does not provide the treatment " +
                    "or employee is not associated with the service point")
        }

        validateAvailability(bookingDto.date, servicePoint, employee, treatment)
    }

    private fun validateAvailability(
        date: LocalDateTime,
        servicePoint: ServicePoint,
        employee: Employee,
        treatment: Treatment
    ) {
        val availabilities = employee.availabilities

        if(availabilities.isEmpty()) {
            throw IllegalArgumentException("Employee has no availabilities")
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

        val takenSLots = scheduleUtilsService.findTakenSlots(
            allPotentialSlots,
            bookings,
            treatment.duration
        )

        val freeSlots = allPotentialSlots.filterNot { it in takenSLots }

        if(date !in freeSlots) {
            throw IllegalArgumentException("Employee is not available at the specified time")
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
        private const val SLOT_INCREMENT_MINUTES = 15
    }
}