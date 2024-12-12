package source.code.oneclickbooking.service.implementation

import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.ScheduleRequestDto
import source.code.oneclickbooking.dto.response.ScheduleResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.*
import source.code.oneclickbooking.service.declaration.ScheduleService
import source.code.oneclickbooking.specification.BookingSpecification
import source.code.oneclickbooking.specification.SpecificationBuilder
import source.code.oneclickbooking.specification.SpecificationFactory
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ScheduleServiceImpl(
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
        val slotIncrementMinutes = 15

        return if (employeeId != null) {
            handleSingleEmployeeSchedule(
                employees,
                dayOfWeek,
                date,
                slotIncrementMinutes,
                treatment,
                bookings
            )
        } else {
            handleMultipleEmployeesSchedule(
                employees,
                dayOfWeek,
                date,
                slotIncrementMinutes,
                treatment,
                bookings
            )
        }
    }

    private fun findFilteredBookings(filter: FilterDto): List<Booking> {
        val bookingFactory = SpecificationFactory { criteria -> BookingSpecification(criteria) }
        val specificationBuilder = SpecificationBuilder(filter, bookingFactory)
        val specification = specificationBuilder.build()
        return bookingRepository.findAll(specification)
    }

    private fun handleSingleEmployeeSchedule(
        employees: List<Employee>,
        dayOfWeek: java.time.DayOfWeek,
        date: LocalDate,
        slotIncrementMinutes: Int,
        treatment: Treatment,
        bookings: List<Booking>
    ): ScheduleResponseDto {
        val availabilities = employees
            .flatMap { it.availabilities }
            .filter { it.dayOfWeek == dayOfWeek }

        if (availabilities.isEmpty()) {
            return ScheduleResponseDto(emptyList())
        }

        val allPotentialSlots = availabilities
            .flatMap { generatePotentialSlots(date, it, slotIncrementMinutes, treatment.duration) }
            .distinct()

        val takenSlots = findTakenSlots(allPotentialSlots, bookings, treatment.duration)
        val freeSlots = allPotentialSlots.filterNot { it in takenSlots }

        return ScheduleResponseDto(freeSlots)
    }

    private fun handleMultipleEmployeesSchedule(
        employees: List<Employee>,
        dayOfWeek: java.time.DayOfWeek,
        date: LocalDate,
        slotIncrementMinutes: Int,
        treatment: Treatment,
        bookings: List<Booking>
    ): ScheduleResponseDto {
        val freeSlotsForAll = mutableSetOf<LocalDateTime>()

        for (employee in employees) {
            val employeeFreeSlots = getFreeSlotsForEmployee(
                employee,
                dayOfWeek,
                date,
                slotIncrementMinutes,
                treatment,
                bookings
            )
            freeSlotsForAll.addAll(employeeFreeSlots)
        }

        if (freeSlotsForAll.isEmpty()) {
            return ScheduleResponseDto(emptyList())
        }

        return ScheduleResponseDto(freeSlotsForAll.sorted())
    }

    private fun getFreeSlotsForEmployee(
        employee: Employee,
        dayOfWeek: java.time.DayOfWeek,
        date: LocalDate,
        slotIncrementMinutes: Int,
        treatment: Treatment,
        bookings: List<Booking>
    ): List<LocalDateTime> {
        val employeeAvailabilities = employee.availabilities.filter { it.dayOfWeek == dayOfWeek }

        if (employeeAvailabilities.isEmpty()) return emptyList()

        val employeeSlots = employeeAvailabilities
            .flatMap { generatePotentialSlots(date, it, slotIncrementMinutes, treatment.duration) }
            .distinct()

        val employeeBookings = bookings.filter { it.employee?.id == employee.id }

        val takenSlots = findTakenSlots(employeeSlots, employeeBookings, treatment.duration)
        return employeeSlots.filterNot { it in takenSlots }
    }

    private fun extractEmployeeId(filter: FilterDto): Int? {
        return filter.filterCriteria
            .find { it.filterKey.equals("employee", ignoreCase = true) }
            ?.value
            ?.toString()
            ?.toIntOrNull()
    }

    private fun extractServicePointId(filter: FilterDto): Int {
        return filter.filterCriteria
            .find { it.filterKey.equals("servicePoint", ignoreCase = true) }
            ?.value
            ?.toString()
            ?.toIntOrNull()
            ?: throw IllegalArgumentException("Service point id is required")
    }

    private fun extractDate(filter: FilterDto): LocalDate {
        return filter.filterCriteria
            .find { it.filterKey.equals("date", ignoreCase = true) }
            ?.value
            ?.toString()
            ?.let { LocalDate.parse(it) }
            ?: throw IllegalArgumentException("Date is required")
    }

    private fun findEmployee(id: Int): Employee {
        return employeeRepository.findById(id)
            .orElseThrow { RecordNotFoundException(Employee::class, id) }
    }

    private fun findServicePoint(id: Int): ServicePoint {
        return servicePointRepository.findById(id)
            .orElseThrow { RecordNotFoundException(ServicePoint::class, id) }
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
            servicePoint.employeeAssociations
                .map { it.employee }
                .filter { it.treatments.contains(treatment) }
        }
    }

    private fun generatePotentialSlots(
        date: LocalDate,
        availability: EmployeeAvailability,
        incrementMinutes: Int,
        treatmentDuration: Int
    ): List<LocalDateTime> {
        val startDateTime = date.atTime(availability.startTime)
        val endDateTime = date.atTime(availability.endTime)

        val slots = mutableListOf<LocalDateTime>()
        var current = startDateTime

        while (current.plusMinutes(treatmentDuration.toLong()) <= endDateTime) {
            slots.add(current)
            current = current.plusMinutes(incrementMinutes.toLong())
        }

        return slots
    }

    private fun findTakenSlots(
        allPotentialSlots: List<LocalDateTime>,
        bookings: List<Booking>,
        treatmentDuration: Int
    ): Set<LocalDateTime> {
        val takenSlots = mutableSetOf<LocalDateTime>()

        for (booking in bookings) {
            val bookingStart = booking.date
            val bookingDuration = booking.treatment!!.duration
            val bookingEnd = bookingStart.plusMinutes(bookingDuration.toLong())

            val overlappingSlots = allPotentialSlots.filter {
                val slotEnd = it.plusMinutes(treatmentDuration.toLong())
                it < bookingEnd && slotEnd > bookingStart
            }

            takenSlots.addAll(overlappingSlots)
        }

        return takenSlots
    }
}
