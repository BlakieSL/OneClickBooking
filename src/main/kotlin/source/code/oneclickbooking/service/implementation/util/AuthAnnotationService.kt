package source.code.oneclickbooking.service.implementation

import org.springframework.stereotype.Service
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.repository.ReviewRepository
import source.code.oneclickbooking.service.implementation.util.AuthorizationUtil

@Service
class AuthAnnotationService(
    private val reviewRepository: ReviewRepository,
) {
    fun isReviewOwner(reviewId: Int): Boolean {
        val review = reviewRepository.findById(reviewId)
            .orElseThrow { RecordNotFoundException(Review::class, reviewId) }

        return AuthorizationUtil.isOwnerOrAdmin(review.booking.user.id)
    }
}