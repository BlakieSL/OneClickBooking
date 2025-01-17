package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.request.ReviewUpdateDto
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.UserDetails

import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.service.declaration.review.ReviewMappingResolver
import source.code.oneclickbooking.service.declaration.util.TimeProviderService

@Component
class ReviewMapper(
    private val resolver: ReviewMappingResolver,
    private val timeProviderService: TimeProviderService,
) {
    fun toResponseDto(review: Review): ReviewResponseDto {
        return ReviewResponseDto(
            id = review.id!!,
            rating = review.rating,
            date = review.date,
            text = review.text,
            bookingId = review.booking.id!!,
            user = UserDetails(
                id = review.booking.user.id!!,
                name = review.booking.user.name
            ),
            employee = review.booking.employee?.let {
                EmployeeDetails(
                    id = it.id!!,
                    username = it.username
                )
            }
        )
    }

    fun toEntity(dto: ReviewCreateDto): Review {
        return Review.of(
            rating = dto.rating,
            text = dto.text,
            booking = resolver.resolveBooking(dto.bookingId),
            date = timeProviderService.getCurrentTime().toLocalDate()
        )
    }

    fun update(review: Review, dto: ReviewUpdateDto) {
        dto.text?.let { review.text = it }
        dto.rating?.let { review.rating = it }
    }
}