package source.code.oneclickbooking.service.implementation.schedule

import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.other.BookingFilterKey
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.ScheduleRequestDto
import source.code.oneclickbooking.dto.response.ScheduleResponseDto
import source.code.oneclickbooking.exception.LocalizedIllegalArgument
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.utils.ExceptionMessages
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.Employee
import source.code.oneclickbooking.model.ServicePoint
import source.code.oneclickbooking.model.Treatment
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.repository.EmployeeRepository
import source.code.oneclickbooking.repository.ServicePointRepository
import source.code.oneclickbooking.repository.TreatmentRepository
import source.code.oneclickbooking.service.declaration.schedule.ScheduleService
import source.code.oneclickbooking.service.declaration.schedule.ScheduleUtilsService
import source.code.oneclickbooking.specification.BookingSpecification
import source.code.oneclickbooking.specification.SpecificationBuilder
import source.code.oneclickbooking.specification.SpecificationFactory
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ScheduleServiceImpl(
    private val utilsService: ScheduleUtilsService,
    private val bookingRepository: BookingRepository,
    private val employeeRepository: EmployeeRepository,
    private val servicePointRepository: ServicePointRepository,
    private val treatmentRepository: TreatmentRepository,
) : ScheduleService {

    override fun getSchedule(request: ScheduleRequestDto): ScheduleResponseDto {
        val filter = request.filter
        val bookings = findFilteredBookings(filter)

        val servicePointId = extractServicePointId(filter)
        val employeeId = extractEmployeeId(filter)
        val date = extractDate(filter)

        val treatment = findTreatment(request.treatmentId)
        val servicePoint = findServicePoint(servicePointId)
        val employees = getLookedEmployees(employeeId, servicePoint, treatment)

        val dayOfWeek = date.dayOfWeek

        return if (employeeId != null) {
            handleSingleEmployeeSchedule(
                employees,
                dayOfWeek,
                date,
                treatment,
                bookings
            )
        } else {
            handleMultipleEmployeesSchedule(
                employees,
                dayOfWeek,
                date,
                treatment,
                bookings
            )
        }
    }

    private fun findFilteredBookings(filter: FilterDto): List<Booking> {
        val bookingFactory = SpecificationFactory { BookingSpecification(it) }
        val specificationBuilder = SpecificationBuilder(filter, bookingFactory)
        val specification = specificationBuilder.build()
        return bookingRepository.findAll(specification)
    }

    private fun handleSingleEmployeeSchedule(
        employees: List<Employee>,
        dayOfWeek: java.time.DayOfWeek,
        date: LocalDate,
        treatment: Treatment,
        bookings: List<Booking>
    ): ScheduleResponseDto {
        val availabilities = employees
            .flatMap { it.availabilities }
            .filter { it.dayOfWeek == dayOfWeek }

        if (availabilities.isEmpty()) return ScheduleResponseDto(emptyList())

        val allPotentialSlots = availabilities
            .flatMap {
                utilsService.generatePotentialSlots(
                    date,
                    it,
                    SLOT_INCREMENT_MINUTES,
                    treatment.duration
                )
            }.distinct()

        val takenSlots = utilsService.findTakenSlots(allPotentialSlots, bookings, treatment.duration)
        val freeSlots = allPotentialSlots.filterNot { it in takenSlots }

        return ScheduleResponseDto(freeSlots)
    }

    private fun handleMultipleEmployeesSchedule(
        employees: List<Employee>,
        dayOfWeek: java.time.DayOfWeek,
        date: LocalDate,
        treatment: Treatment,
        bookings: List<Booking>
    ): ScheduleResponseDto {
        val freeSlotsForAll = mutableSetOf<LocalDateTime>()

        for (employee in employees) {
            val employeeFreeSlots = getFreeSlotsForEmployee(
                employee,
                dayOfWeek,
                date,
                treatment,
                bookings
            )
            freeSlotsForAll.addAll(employeeFreeSlots)
        }

        return ScheduleResponseDto(freeSlotsForAll.sorted())
    }

    private fun getFreeSlotsForEmployee(
        employee: Employee,
        dayOfWeek: java.time.DayOfWeek,
        date: LocalDate,
        treatment: Treatment,
        bookings: List<Booking>
    ): List<LocalDateTime> {
        val employeeAvailabilities = employee.availabilities
            .filter { it.dayOfWeek == dayOfWeek }

        if (employeeAvailabilities.isEmpty()) return emptyList()

        val employeeSlots = employeeAvailabilities
            .flatMap {
                utilsService.generatePotentialSlots(
                    date,
                    it,
                    SLOT_INCREMENT_MINUTES,
                    treatment.duration
                )
            }.distinct()

        val employeeBookings = bookings
            .filter { it.employee?.id == employee.id }

        val takenSlots = utilsService.findTakenSlots(
            employeeSlots,
            employeeBookings,
            treatment.duration
        )

        return employeeSlots.filterNot { it in takenSlots }
    }

    private fun extractEmployeeId(filter: FilterDto): Int? {
        return filter.filterCriteria
            .find { it.filterKey.equals(BookingFilterKey.EMPLOYEE.name, ignoreCase = true) }
            ?.value
            ?.toString()
            ?.toIntOrNull()
    }

    private fun extractServicePointId(filter: FilterDto): Int {
        return filter.filterCriteria
            .find { it.filterKey.equals(BookingFilterKey.SERVICE_POINT.name, ignoreCase = true) }
            ?.value
            ?.toString()
            ?.toIntOrNull()
            ?: throw LocalizedIllegalArgument(ExceptionMessages.SERVICE_POINT_REQUIRED)
    }

    private fun extractDate(filter: FilterDto): LocalDate {
        val date = filter.filterCriteria
            .find { it.filterKey.equals(BookingFilterKey.DATE.name, ignoreCase = true) }
            ?.value
            ?.toString()
            ?.let { LocalDate.parse(it) }
            ?: throw LocalizedIllegalArgument(ExceptionMessages.DATE_REQUIRED)

        if (date.isBefore(LocalDate.now())) {
            throw LocalizedIllegalArgument(ExceptionMessages.DATE_IN_PAST)
        }

        return date
    }

    private fun findEmployee(id: Int): Employee {
        return employeeRepository.findByIdWithAvailabilities(id)
            ?: throw RecordNotFoundException(Employee::class, id)
    }

    private fun findServicePoint(id: Int): ServicePoint {
        return servicePointRepository.findByIdWithAssociations(id)
            ?: throw RecordNotFoundException(ServicePoint::class, id)
    }

    private fun findTreatment(id: Int): Treatment {
        return treatmentRepository.findById(id)
            .orElseThrow { RecordNotFoundException(Treatment::class, id) }
    }

    private fun getLookedEmployees(
        employeeId: Int?,
        servicePoint: ServicePoint,
        treatment: Treatment
    ): List<Employee> {
        return if (employeeId != null) {
            val employee = findEmployee(employeeId)
            listOf(employee)
        } else {
            employeeRepository.findAllByServicePointIdAndTreatmentId(
                servicePoint.id!!,
                treatment.id!!
            )
        }
    }

    companion object {
        private const val SLOT_INCREMENT_MINUTES: Int = 15
    }
}
