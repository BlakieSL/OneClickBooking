package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.request.ReviewUpdateDto
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.service.declaration.review.ReviewMappingResolver

@Component
class ReviewMapper(
    private val resolver: ReviewMappingResolver
) {
    fun toResponseDto(review: Review): ReviewResponseDto {
        return ReviewResponseDto(
            id = review.id!!,
            rating = review.rating,
            text = review.text,
            bookingId = review.booking.id!!
        )
    }

    fun toEntity(dto: ReviewCreateDto): Review {
        return Review.of(
            rating = dto.rating,
            text = dto.text,
            booking = resolver.resolveBooking(dto.bookingId)
        )
    }

    fun update(review: Review, dto: ReviewUpdateDto) {
        dto.text?.let { review.text = it }
        dto.rating?.let { review.rating = it }
    }
}