package source.code.oneclickbooking.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.dto.request.ScheduleRequestDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.repository.EmployeeRepository
import source.code.oneclickbooking.repository.ServicePointRepository
import source.code.oneclickbooking.repository.TreatmentRepository
import source.code.oneclickbooking.service.implementation.ScheduleServiceImpl
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeParseException
import java.util.*

@ExtendWith(MockitoExtension::class)
class ScheduleServiceTest {
    @Mock
    private lateinit var bookingRepository: BookingRepository

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    @Mock
    private lateinit var servicePointRepository: ServicePointRepository

    @Mock
    private lateinit var treatmentRepository: TreatmentRepository

    @InjectMocks
    private lateinit var scheduleService: ScheduleServiceImpl

    private lateinit var servicePoint: ServicePoint
    private lateinit var employee1: Employee
    private lateinit var employee2: Employee
    private lateinit var treatment: Treatment
    private lateinit var availability1: EmployeeAvailability
    private lateinit var availability2: EmployeeAvailability

    private val date = LocalDate.of(2024, 12, 15)
    private val morningSlot = date.atTime(LocalTime.of(9, 0))
    private val morningEnd = date.atTime(LocalTime.of(12, 0))

    @BeforeEach
    fun setUp() {
        servicePoint = ServicePoint.createDefault(id = 1)
        employee1 = Employee.createDefault(id = 10)
        employee2 = Employee.createDefault(id = 20)
        treatment = Treatment.createDefault(id = 100, duration = 30)

        availability1 = EmployeeAvailability(
            id = 1,
            dayOfWeek = DayOfWeek.SUNDAY,
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(12, 0),
        )
        availability1.employee = employee1

        availability2 = EmployeeAvailability(
            id = 2,
            dayOfWeek = DayOfWeek.SUNDAY,
            startTime = LocalTime.of(9, 0),
            endTime = LocalTime.of(10, 0)
        )

        availability2.employee = employee2

        employee1.availabilities.add(availability1)
        employee2.availabilities.add(availability2)

        val spEmployee1 = ServicePointEmployee.of(1, servicePoint, employee1)
        servicePoint.employeeAssociations.add(spEmployee1)
        employee1.servicePointAssociations.add(spEmployee1)

        val spEmployee2 = ServicePointEmployee.of(2, servicePoint, employee2)
        servicePoint.employeeAssociations.add(spEmployee2)
        employee2.servicePointAssociations.add(spEmployee2)
    }

    @Test
    fun `should throw exception if service point id filter is missing`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("date", "2024-12-15", FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)

        assertThrows<IllegalArgumentException> {
            scheduleService.getSchedule(request)
        }
    }

    @Test
    fun `should throw exception if date filter is missing`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("servicePoint", 1, FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)

        assertThrows<IllegalArgumentException> {
            scheduleService.getSchedule(request)
        }
    }

    @Test
    fun `should throw exception if date filter has invalid format`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("SERVICE_POINT", 1, FilterOperation.EQUAL),
            FilterCriteria("DATE", "invalid-date", FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)

        assertThrows<DateTimeParseException> {
            scheduleService.getSchedule(request)
        }
    }

    @Test
    fun `should throw exception if service point not found`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("SERVICE_POINT", 999, FilterOperation.EQUAL),
            FilterCriteria("DATE", "2024-12-15", FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)

        whenever(treatmentRepository.findById(100))
            .thenReturn(Optional.of(Treatment.createDefault(id = 100)))
        whenever(servicePointRepository.findByIdWithAssociations(999)).thenReturn(null)

        assertThrows<RecordNotFoundException> {
            scheduleService.getSchedule(request)
        }
    }

    @Test
    fun `should throw exception if treatment not found`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("SERVICE_POINT", 1, FilterOperation.EQUAL),
            FilterCriteria("DATE", "2024-12-15", FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 999)

        whenever(treatmentRepository.findById(999))
            .thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            scheduleService.getSchedule(request)
        }
    }

    @Test
    fun `should return all free slots for single employee when no bookings`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("SERVICE_POINT", 1, FilterOperation.EQUAL),
            FilterCriteria("DATE", date.toString(), FilterOperation.EQUAL),
            FilterCriteria("EMPLOYEE", 10, FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)

        whenever(servicePointRepository.findByIdWithAssociations(1)).thenReturn(servicePoint)
        whenever(employeeRepository.findByIdWithAssociations(10)).thenReturn(employee1)
        whenever(treatmentRepository.findById(100)).thenReturn(Optional.of(treatment))
        whenever(bookingRepository.findAll(any<Specification<Booking>>())).thenReturn(emptyList())

        val result = scheduleService.getSchedule(request)

        val expectedSlots = generateSequence(date.atTime(9,0)) { it.plusMinutes(15) }
            .takeWhile { it.plusMinutes(30) <= date.atTime(12,0) }
            .toList()

        assertEquals(expectedSlots, result.freeSlots)
    }

    @Test
    fun `should exclude slots overlapping with a booking (single employee)`() {
        val booking = Booking.createDefault(
            id = 1,
            date = date.atTime(9,30),
            servicePoint = servicePoint,
            employee = employee1,
            treatment = treatment
        )
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("SERVICE_POINT", 1, FilterOperation.EQUAL),
            FilterCriteria("DATE", date.toString(), FilterOperation.EQUAL),
            FilterCriteria("EMPLOYEE", 10, FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)

        whenever(servicePointRepository.findByIdWithAssociations(1)).thenReturn(servicePoint)
        whenever(employeeRepository.findByIdWithAssociations(10)).thenReturn(employee1)
        whenever(treatmentRepository.findById(100)).thenReturn(Optional.of(treatment))
        whenever(bookingRepository.findAll(any<Specification<Booking>>())).thenReturn(listOf(booking))

        val result = scheduleService.getSchedule(request)

        val expectedSlots = generateSequence(date.atTime(9,0)) { it.plusMinutes(15) }
            .takeWhile { it.plusMinutes(30) <= date.atTime(12,0) }
            .filter {
                it != date.atTime(9,30)
                        && it != date.atTime(9,45)
                        && it != date.atTime(9,15)
            }
            .toList()

        assertEquals(expectedSlots, result.freeSlots)
    }

    @Test
    fun `should return union of free slots from multiple employees`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("SERVICE_POINT", 1, FilterOperation.EQUAL),
            FilterCriteria("DATE", date.toString(), FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)

        whenever(servicePointRepository.findByIdWithAssociations(1)).thenReturn(servicePoint)
        whenever(treatmentRepository.findById(100)).thenReturn(Optional.of(treatment))
        whenever(bookingRepository.findAll(any<Specification<Booking>>())).thenReturn(emptyList())
        whenever(employeeRepository.findAllByServicePointIdAndTreatmentId(1, 100))
            .thenReturn(listOf(employee1, employee2))
        val result = scheduleService.getSchedule(request)

        assertTrue(result.freeSlots.isNotEmpty())
        assertTrue(result.freeSlots.contains(date.atTime(11,30)))
    }

    @Test
    fun `should keep a slot if another employee is free in multiple employees scenario`() {
        val filter = FilterDto(filterCriteria = listOf(
            FilterCriteria("SERVICE_POINT", 1, FilterOperation.EQUAL),
            FilterCriteria("DATE", date.toString(), FilterOperation.EQUAL)
        ))
        val request = ScheduleRequestDto(filter = filter, treatmentId = 100)
        val booking = Booking.createDefault(
            id = 1,
            date = date.atTime(9,30),
            servicePoint = servicePoint,
            employee = employee1,
            treatment = treatment
        )

        whenever(servicePointRepository.findByIdWithAssociations(1)).thenReturn(servicePoint)
        whenever(treatmentRepository.findById(100)).thenReturn(Optional.of(treatment))
        whenever(bookingRepository.findAll(any<Specification<Booking>>())).thenReturn(listOf(booking))
        whenever(employeeRepository.findAllByServicePointIdAndTreatmentId(1, 100))
            .thenReturn(listOf(employee1, employee2))

        val result = scheduleService.getSchedule(request)

        println(result)
        assertTrue(result.freeSlots.contains(date.atTime(9,30)))
    }
}