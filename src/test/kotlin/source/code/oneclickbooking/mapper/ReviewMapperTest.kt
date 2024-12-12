package source.code.oneclickbooking.mapper

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
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.service.declaration.review.ReviewMappingResolver

@ExtendWith(MockitoExtension::class)
class ReviewMapperTest {

    @Mock
    private lateinit var resolver: ReviewMappingResolver

    @InjectMocks
    private lateinit var reviewMapper: ReviewMapper

    private lateinit var booking: Booking
    private lateinit var review: Review
    private lateinit var reviewCreateDto: ReviewCreateDto
    private lateinit var reviewUpdateDto: ReviewUpdateDto

    @BeforeEach
    fun setUp() {
        booking = Booking.createDefault(id = 1)
        review = Review(
            id = 1,
            rating = 5,
            text = "Great service!",
        ).apply { booking = booking}
        reviewCreateDto = ReviewCreateDto(
            rating = 5,
            text = "Great service!",
            bookingId = 1
        )
        reviewUpdateDto = ReviewUpdateDto(
            rating = 4,
            text = "Updated review"
        )
    }

    @Test
    fun `should map ReviewCreateDto to Review`() {
        whenever(resolver.resolveBooking(1)).thenReturn(booking)

        val result = reviewMapper.toEntity(reviewCreateDto)

        assertEquals(5, result.rating)
        assertEquals("Great service!", result.text)
        assertEquals(1, result.booking.id)
    }

    @Test
    fun `should map Review to ReviewResponseDto`() {
        val result = reviewMapper.toResponseDto(review)

        assertEquals(1, result.id)
        assertEquals(5, result.rating)
        assertEquals("Great service!", result.text)
        assertEquals(1, result.bookingId)
    }

    @Test
    fun `should update Review fields from ReviewUpdateDto`() {
        reviewMapper.update(review, reviewUpdateDto)

        assertEquals(4, review.rating)
        assertEquals("Updated review", review.text)
    }

    @Test
    fun `should update Review text only`() {
        val partialUpdateDto = ReviewUpdateDto(
            text = "Partial update"
        )

        reviewMapper.update(review, partialUpdateDto)

        assertEquals(5, review.rating)
        assertEquals("Partial update", review.text)
    }
}