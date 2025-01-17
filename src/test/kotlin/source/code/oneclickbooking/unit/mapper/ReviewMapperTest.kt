package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.request.ReviewUpdateDto
import source.code.oneclickbooking.mapper.ReviewMapper
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.service.declaration.review.ReviewMappingResolver
import source.code.oneclickbooking.service.declaration.util.TimeProviderService
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class ReviewMapperTest {

    @Mock
    private lateinit var resolver: ReviewMappingResolver

    @Mock
    private lateinit var timeProviderService: TimeProviderService

    @InjectMocks
    private lateinit var reviewMapper: ReviewMapper

    private lateinit var booking: Booking
    private lateinit var review: Review
    private lateinit var createDto: ReviewCreateDto
    private lateinit var updateDto: ReviewUpdateDto
    private val date = LocalDate.of(2021, 1, 1);

    @BeforeEach
    fun setUp() {
        booking = Booking.createDefault(id = 1)
        review = Review.of(id = 1, rating = 5, text = "Great service!", booking = booking, LocalDate.now())
        createDto = ReviewCreateDto(rating = 5, text = "Great service!", bookingId = 1)
        updateDto = ReviewUpdateDto(rating = 4, text = "Updated review")
    }

    @Test
    fun `should map ReviewCreateDto to Review`() {
        whenever(resolver.resolveBooking(1)).thenReturn(booking)
        whenever(timeProviderService.getCurrentTime()).thenReturn(LocalDateTime.now())

        val result = reviewMapper.toEntity(createDto)

        assertEquals(createDto.rating, result.rating)
        assertEquals(createDto.text, result.text)
        assertEquals(createDto.bookingId, result.booking.id)
    }

    @Test
    fun `should map Review to ReviewResponseDto`() {
        val result = reviewMapper.toResponseDto(review)

        assertEquals(review.id, result.id)
        assertEquals(review.rating, result.rating)
        assertEquals(review.text, result.text)
        assertEquals(review.booking.id, result.bookingId)
        assertEquals(review.date, result.date)
        assertEquals(review.booking.employee?.id, result.employee?.id)
        assertEquals(review.booking.employee?.username, result.employee?.username)
        assertEquals(review.booking.user.id, result.user.id)
        assertEquals(review.booking.user.name, result.user.name)
    }

    @Test
    fun `should update Review fields from ReviewUpdateDto`() {
        reviewMapper.update(review, updateDto)

        assertEquals(updateDto.rating, review.rating)
        assertEquals(updateDto.text, review.text)
    }

    @Test
    fun `should update Review text only`() {
        val partialUpdateDto = ReviewUpdateDto(text = "Partial update")

        reviewMapper.update(review, partialUpdateDto)

        assertEquals(5, review.rating)
        assertEquals(partialUpdateDto.text, review.text)
    }
}