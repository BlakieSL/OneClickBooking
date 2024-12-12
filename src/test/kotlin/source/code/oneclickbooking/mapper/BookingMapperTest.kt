package source.code.oneclickbooking.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.service.declaration.booking.BookingMappingResolver
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class BookingMapperTest {

    @Mock
    private lateinit var resolver: BookingMappingResolver

    @InjectMocks
    private lateinit var bookingMapper: BookingMapper

    private lateinit var user: User
    private lateinit var servicePoint: ServicePoint
    private lateinit var employee: Employee
    private lateinit var treatment: Treatment
    private lateinit var booking: Booking
    private lateinit var bookingCreateDto: BookingCreateDto
    private lateinit var bookingUpdateDto: BookingUpdateDto

    @BeforeEach
    fun setUp() {
        user = User.createDefault(id = 1)
        servicePoint = ServicePoint.createDefault(id = 1)
        employee = Employee.createDefault(id = 1)
        treatment = Treatment.createDefault(id = 1)
        booking = Booking(
            id = 1,
            date = LocalDateTime.of(2023, 10, 10, 10, 0)
        ).apply {
            user = user
            servicePoint = servicePoint
            employee = employee
            treatment = treatment
        }
        bookingCreateDto = BookingCreateDto(
            servicePointId = 1,
            userId = 1,
            date = LocalDateTime.of(2023, 10, 10, 10, 0),
            employeeId = 1,
            treatmentId = 1
        )
        bookingUpdateDto = BookingUpdateDto(
            date = LocalDateTime.of(2023, 11, 11, 11, 0),
            userId = 1,
            servicePointId = 1,
            employeeId = 1,
            treatmentId = 1
        )
    }

    @Test
    fun `should map BookingCreateDto to Booking`() {
        whenever(resolver.resolveUser(1)).thenReturn(user)
        whenever(resolver.resolveServicePoint(1)).thenReturn(servicePoint)
        whenever(resolver.resolveEmployee(1)).thenReturn(employee)
        whenever(resolver.resolveTreatment(1)).thenReturn(treatment)

        val result = bookingMapper.toEntity(bookingCreateDto)

        assertEquals(1, result.user.id)
        assertEquals(1, result.servicePoint.id)
        assertEquals(LocalDateTime.of(2023, 10, 10, 10, 0), result.date)
    }

    @Test
    fun `should map Booking to BookingResponseDto`() {
        val result = bookingMapper.toResponseDto(booking)

        assertEquals(1, result.id)
        assertEquals(1, result.userId)
        assertEquals(1, result.servicePointId)
        assertEquals(LocalDateTime.of(2023, 10, 10, 10, 0), result.date)
    }

    @Test
    fun `should update Booking fields from BookingUpdateDto`() {
        val newTreatment = Treatment.createDefault(id = 2)
        whenever(resolver.resolveUser(1)).thenReturn(user)
        whenever(resolver.resolveServicePoint(1)).thenReturn(servicePoint)
        whenever(resolver.resolveEmployee(1)).thenReturn(employee)
        whenever(resolver.resolveTreatment(1)).thenReturn(newTreatment)

        bookingMapper.update(booking, bookingUpdateDto)

        assertEquals(LocalDateTime.of(2023, 11, 11, 11, 0), booking.date)
        assertEquals(newTreatment.id, booking.treatment?.id)
    }

    @Test
    fun `should update Booking date only`() {
        val partialUpdateDto = BookingUpdateDto(
            date = LocalDateTime.of(2023, 10, 10, 10, 59),
            userId = null,
            servicePointId = null,
            employeeId = null,
            treatmentId = null
        )

        bookingMapper.update(booking, partialUpdateDto)

        assertEquals(LocalDateTime.of(2023, 10, 10, 10, 59), booking.date)
        assertEquals(1, booking.user.id)
        assertEquals(1, booking.servicePoint.id)
        assertEquals(1, booking.employee?.id)
        assertEquals(1, booking.treatment?.id)
    }
}
