package source.code.oneclickbooking.unit.service.review

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.junit.jupiter.api.Assertions.assertEquals
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
import org.springframework.context.ApplicationEventPublisher
import source.code.oneclickbooking.config.MessageResolverHolder
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.request.ReviewUpdateDto
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.UserDetails
import source.code.oneclickbooking.exception.LocalizedIllegalArgument
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.ReviewMapper
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.BookingStatus
import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.repository.ReviewRepository
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import source.code.oneclickbooking.service.declaration.util.ValidationService
import source.code.oneclickbooking.service.implementation.review.ReviewServiceImpl
import source.code.oneclickbooking.service.implementation.util.MessageResolver
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class ReviewServiceTest {

    @Mock
    private lateinit var validationService: ValidationService

    @Mock
    private lateinit var jsonPatchService: JsonPatchService

    @Mock
    private lateinit var mapper: ReviewMapper

    @Mock
    private lateinit var repository: ReviewRepository

    @Mock
    private lateinit var eventPublisher: ApplicationEventPublisher

    @Mock
    private lateinit var bookingRepository: BookingRepository

    @Mock
    private lateinit var messageResolver: MessageResolver

    @InjectMocks
    private lateinit var reviewService: ReviewServiceImpl

    private lateinit var review: Review
    private lateinit var reviewCreateDto: ReviewCreateDto
    private lateinit var reviewUpdateDto: ReviewUpdateDto
    private lateinit var reviewResponseDto: ReviewResponseDto
    private lateinit var booking: Booking
    private val date = LocalDate.of(2021, 1, 1);

    @BeforeEach
    fun setUp() {
        MessageResolverHolder.messageResolver = messageResolver

        booking = Booking.createDefault(id = 1)

        review = Review.of(
            id = 1,
            rating = 5,
            text = "Great service!",
            booking = booking,
            LocalDate.now()
        )

        reviewCreateDto = ReviewCreateDto(
            rating = 5,
            text = "Great service!",
            bookingId = 1
        )

        reviewUpdateDto = ReviewUpdateDto(
            rating = 4,
            text = "Updated review"
        )

        reviewResponseDto = ReviewResponseDto(
            id = review.id!!,
            rating = review.rating,
            date = review.date,
            text = review.text,
            bookingId = booking.id!!,
            user = UserDetails(
                id = booking.user.id!!,
                name = booking.user.name
            ),
            employee = booking.employee?.let {
                EmployeeDetails(
                    id = it.id!!,
                    username = it.username
                )
            }
        )
    }

    @Test
    fun `should create review`() {
        val savedReview = Review.of(
            id = 1,
            rating = review.rating,
            text = review.text,
            booking = review.booking,
            date = review.date
        )
        booking.status = BookingStatus.COMPLETED

        whenever(bookingRepository.findById(1)).thenReturn(Optional.of(booking))
        whenever(mapper.toEntity(reviewCreateDto)).thenReturn(review)
        whenever(repository.save(review)).thenReturn(savedReview)
        whenever(mapper.toResponseDto(savedReview)).thenReturn(reviewResponseDto)

        val result = reviewService.create(reviewCreateDto)

        assertEquals(reviewResponseDto, result)
        verify(mapper).toResponseDto(savedReview)
    }

    @Test
    fun `should thow RecordNotFound when associated booking not found`() {
        whenever(bookingRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            reviewService.create(reviewCreateDto)
        }
    }

    @Test
    fun `should throw LocalizedIllegalArgument when booking is not COMPLETED`() {
        whenever(bookingRepository.findById(1)).thenReturn(Optional.of(booking))
        assertThrows<LocalizedIllegalArgument> {
            reviewService.create(reviewCreateDto)
        }
    }

    @Test
    fun `should update review successfully`() {
        val patch = mock<JsonMergePatch>()
        val patchedDto = reviewUpdateDto.copy()

        whenever(repository.findById(1)).thenReturn(Optional.of(review))
        whenever(jsonPatchService.applyPatch(patch, ReviewUpdateDto(), ReviewUpdateDto::class))
            .thenReturn(patchedDto)
        whenever(mapper.toResponseDto(review)).thenReturn(reviewResponseDto)
        whenever(repository.save(review)).thenReturn(review)
        val result = reviewService.update(1, patch)

        assertEquals(reviewResponseDto, result)
    }

    @Test
    fun `should throw exception when updating non-existent review`() {
        val patch = mock<JsonMergePatch>()

        whenever(repository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            reviewService.update(1, patch)
        }
    }

    @Test
    fun `should delete review successfully`() {
        whenever(repository.findById(1)).thenReturn(Optional.of(review))

        reviewService.delete(1)

        verify(repository).delete(review)
    }

    @Test
    fun `should throw exception when deleting non-existent review`() {
        whenever(repository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            reviewService.delete(1)
        }
    }

    @Test
    fun `should get review successfully`() {
        whenever(repository.findById(1)).thenReturn(Optional.of(review))
        whenever(mapper.toResponseDto(review)).thenReturn(reviewResponseDto)

        val result = reviewService.get(1)

        assertEquals(reviewResponseDto, result)
    }

    @Test
    fun `should throw exception when getting non-existent review`() {
        whenever(repository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            reviewService.get(1)
        }
    }

    @Test
    fun `should get all reviews successfully`() {
        val reviews = listOf(review)
        val reviewResponseDtos = listOf(reviewResponseDto)

        whenever(repository.findAll()).thenReturn(reviews)
        whenever(mapper.toResponseDto(review)).thenReturn(reviewResponseDto)

        val result = reviewService.getAll()

        assertEquals(reviewResponseDtos, result)
    }
}