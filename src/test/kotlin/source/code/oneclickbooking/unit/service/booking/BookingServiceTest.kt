package source.code.oneclickbooking.unit.service.booking

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.BookingMapper
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.*
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import source.code.oneclickbooking.service.declaration.util.ValidationService
import source.code.oneclickbooking.service.implementation.booking.BookingMappingResolverImpl
import source.code.oneclickbooking.service.implementation.booking.BookingServiceImpl
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookingServiceTest {

    @Mock
    private lateinit var validationService: ValidationService

    @Mock
    private lateinit var jsonPatchService: JsonPatchService

    @Mock
    private lateinit var mapper: BookingMapper

    @Mock
    private lateinit var mappingResolver: BookingMappingResolverImpl

    @Mock
    private lateinit var repository: BookingRepository

    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var bookingService: BookingServiceImpl

    private lateinit var booking: Booking
    private lateinit var bookingCreateDto: BookingCreateDto
    private lateinit var bookingUpdateDto: BookingUpdateDto
    private lateinit var bookingResponseDto: BookingResponseDto
    private lateinit var user: User
    private lateinit var servicePoint: ServicePoint
    private lateinit var employee: Employee
    private lateinit var treatment: Treatment


    @BeforeEach
    fun setUp() {
        user = User.createDefault(id = 1)
        servicePoint = ServicePoint.createDefault(id = 1)
        employee = Employee.createDefault(id = 1)
        treatment = Treatment.createDefault(id = 1)

        booking = Booking.createDefault(
            id = 1,
            date = LocalDateTime.of(2023, 10, 10, 10, 0),
            user = user,
            servicePoint = servicePoint,
            employee = employee,
            treatment = treatment
        )


        bookingCreateDto = BookingCreateDto(
            date = LocalDateTime.of(2023, 10, 10, 10, 0),
            userId = user.id!!,
            servicePointId = servicePoint.id!!,
            employeeId = employee.id!!,
            treatmentId = treatment.id!!
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
    fun `should create booking`() {
        val savedBooking = Booking.of(
            id = 1,
            date = booking.date,
            user = booking.user,
            servicePoint = booking.servicePoint,
            employee = booking.employee,
            treatment = booking.treatment,
            review = booking.review
        )
        whenever(mapper.toEntity(bookingCreateDto)).thenReturn(booking)
        whenever(repository.save(booking)).thenReturn(savedBooking)
        whenever(mapper.toResponseDto(savedBooking)).thenReturn(bookingResponseDto)

        val result = bookingService.create(bookingCreateDto)

        assertEquals(bookingResponseDto, result)
        verify(mapper).toResponseDto(savedBooking)
    }

    @Test
    fun `should update booking successfully`() {
        val patch = mock<JsonMergePatch>()
        val patchedDto = bookingUpdateDto.copy()

        whenever(repository.findById(1)).thenReturn(Optional.of(booking))
        whenever(jsonPatchService.applyPatch(patch, bookingResponseDto, BookingUpdateDto::class))
            .thenReturn(patchedDto)
        whenever(mapper.toResponseDto(booking)).thenReturn(bookingResponseDto)
        whenever(repository.save(booking)).thenReturn(booking)
        val result = bookingService.update(1, patch)

        assertEquals(bookingResponseDto, result)
    }

    @Test
    fun `should throw exception when updating non-existent booking`() {
        val patch = mock<JsonMergePatch>()

        whenever(repository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            bookingService.update(1, patch)
        }
    }

    @Test
    fun `should delete booking successfully`() {
        whenever(repository.findById(1)).thenReturn(Optional.of(booking))

        bookingService.delete(1)

        verify(repository).delete(booking)
    }

    @Test
    fun `should throw exception when deleting non-existent booking`() {
        whenever(repository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            bookingService.delete(1)
        }
    }

    @Test
    fun `should get booking successfully`() {
        whenever(repository.findById(1)).thenReturn(Optional.of(booking))
        whenever(mapper.toResponseDto(booking)).thenReturn(bookingResponseDto)

        val result = bookingService.get(1)

        assertEquals(bookingResponseDto, result)
    }

    @Test
    fun `should throw exception when getting non-existent booking`() {
        whenever(repository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            bookingService.get(1)
        }
    }

    @Test
    fun `should get all bookings successfully`() {
        val bookings = listOf(booking)
        val bookingResponseDtos = listOf(bookingResponseDto)

        whenever(repository.findAll()).thenReturn(bookings)
        whenever(mapper.toResponseDto(booking)).thenReturn(bookingResponseDto)

        val result = bookingService.getAll()

        assertEquals(bookingResponseDtos, result)
    }
}