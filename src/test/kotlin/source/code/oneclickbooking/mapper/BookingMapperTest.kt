package source.code.oneclickbooking.mapper

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mapstruct.factory.Mappers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.*
import source.code.oneclickbooking.service.declaration.booking.BookingMappingResolverService
import source.code.oneclickbooking.service.implementation.booking.BookingMappingResolverServiceImpl
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookingMapperTest {
    private val bookingMapper = Mappers.getMapper(BookingMapper::class.java)

    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var servicePointRepository: ServicePointRepository
    @Mock
    private lateinit var employeeRepository: EmployeeRepository
    @Mock
    private lateinit var treatmentRepository: TreatmentRepository
    @InjectMocks
    private lateinit var bookingMappingResolverServiceImpl: BookingMappingResolverServiceImpl

    private lateinit var user: User
    private lateinit var servicePoint: ServicePoint
    private lateinit var employee: Employee
    private lateinit var treatment: Treatment
    private lateinit var booking: Booking
    private lateinit var bookingCreateDto: BookingCreateDto
    private lateinit var bookingUpdateDto: BookingUpdateDto
    private lateinit var bookingResponseDto: BookingResponseDto

    @BeforeEach
    fun setUp() {
        user = User.createDefault(id = 1)
        servicePoint = ServicePoint.createDefault(id = 1)
        employee = Employee.createDefault(id = 1)
        treatment = Treatment.createDefault(id = 1)
        booking = Booking(
            id = 1,
            user = user,
            servicePoint = servicePoint,
            employee = employee,
            treatment = treatment,
            date = LocalDateTime.of(2023, 10, 10, 10, 0)
        )
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
        bookingResponseDto = BookingResponseDto(
            id = 1,
            date = LocalDateTime.of(2023, 10, 10, 10, 0),
            userId = 1,
            servicePointId = 1,
            employeeId = 1,
            treatmentId = 1,
            reviewId = null
        )
    }

    @Test
    fun `should map BookingCreateDto to Booking`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(servicePointRepository.findById(1)).thenReturn(Optional.of(servicePoint))
        whenever(employeeRepository.findById(1)).thenReturn(Optional.of(employee))
        whenever(treatmentRepository.findById(1)).thenReturn(Optional.of(treatment))


        val result = bookingMapper.toEntity(bookingCreateDto, bookingMappingResolverServiceImpl)

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
        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(servicePointRepository.findById(1)).thenReturn(Optional.of(servicePoint))
        whenever(employeeRepository.findById(1)).thenReturn(Optional.of(employee))
        whenever(treatmentRepository.findById(1)).thenReturn(Optional.of(newTreatment))

        bookingMapper.update(booking, bookingUpdateDto, bookingMappingResolverServiceImpl)

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

        bookingMapper.update(booking, partialUpdateDto, bookingMappingResolverServiceImpl)

        assertEquals(LocalDateTime.of(2023, 10, 10, 10, 59), booking.date)
        assertEquals(1, booking.user.id)
        assertEquals(1, booking.servicePoint.id)
        assertEquals(1, booking.employee?.id)
        assertEquals(1, booking.treatment?.id)
    }
}