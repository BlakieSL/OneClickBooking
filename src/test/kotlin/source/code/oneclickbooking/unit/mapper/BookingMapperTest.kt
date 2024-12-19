package source.code.oneclickbooking.unit.mapper

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
import source.code.oneclickbooking.mapper.BookingMapper
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
    private lateinit var createDto: BookingCreateDto
    private lateinit var updateDto: BookingUpdateDto

    @BeforeEach
    fun setUp() {
        user = User.createDefault(id = 1)
        servicePoint = ServicePoint.createDefault(id = 1)
        employee = Employee.createDefault(id = 1)
        treatment = Treatment.createDefault(id = 1)
        booking = Booking.of(
            id = 1,
            date = LocalDateTime.of(2023, 10, 10, 10, 0),
            user = user,
            servicePoint = servicePoint,
            employee = employee,
            treatment = treatment
        )
        createDto = BookingCreateDto(
            servicePointId = 1,
            userId = 1,
            date = LocalDateTime.of(2023, 10, 10, 10, 0),
            employeeId = 1,
            treatmentId = 1
        )
        updateDto = BookingUpdateDto(
            date = LocalDateTime.of(2023, 11, 11, 11, 0),
            userId = 1,
            servicePointId = 1,
            employeeId = 1,
            treatmentId = 2
        )
    }

    @Test
    fun `should map BookingCreateDto to Booking`() {
        whenever(resolver.resolveUser(createDto.userId)).thenReturn(user)
        whenever(resolver.resolveServicePoint(createDto.servicePointId)).thenReturn(servicePoint)
        whenever(resolver.resolveEmployee(createDto.employeeId)).thenReturn(employee)
        whenever(resolver.resolveTreatment(createDto.treatmentId)).thenReturn(treatment)

        val result = bookingMapper.toEntity(createDto)

        assertEquals(createDto.userId, result.user.id)
        assertEquals(createDto.servicePointId, result.servicePoint.id)
        assertEquals(createDto.employeeId, result.employee?.id)
        assertEquals(createDto.treatmentId, result.treatment?.id)
        assertEquals(createDto.date, result.date)
    }

    @Test
    fun `should map Booking to BookingResponseDto`() {
        val result = bookingMapper.toResponseDto(booking)

        assertEquals(booking.id, result.id)
        assertEquals(booking.user.id, result.userId)
        assertEquals(booking.servicePoint.id, result.servicePointId)
        assertEquals(booking.employee?.id, result.employeeId)
        assertEquals(booking.treatment?.id, result.treatmentId)
        assertEquals(booking.date, result.date)
    }

    @Test
    fun `should update Booking fields from BookingUpdateDto`() {
        val newTreatment = Treatment.createDefault(id = 2)
        whenever(resolver.resolveUser(updateDto.userId)).thenReturn(user)
        whenever(resolver.resolveServicePoint(updateDto.servicePointId)).thenReturn(servicePoint)
        whenever(resolver.resolveEmployee(updateDto.employeeId)).thenReturn(employee)
        whenever(resolver.resolveTreatment(updateDto.treatmentId)).thenReturn(newTreatment)

        bookingMapper.update(booking, updateDto)

        assertEquals(updateDto.date, booking.date)
        assertEquals(newTreatment.id, booking.treatment?.id)
    }

    @Test
    fun `should update Booking date only`() {
        val partialUpdateDto = BookingUpdateDto(
            date = LocalDateTime.of(2023, 10, 10, 10, 59)
        )

        bookingMapper.update(booking, partialUpdateDto)

        assertEquals(partialUpdateDto.date, booking.date)
        assertEquals(1, booking.user.id)
        assertEquals(1, booking.servicePoint.id)
        assertEquals(1, booking.employee?.id)
        assertEquals(1, booking.treatment?.id)
    }
}
