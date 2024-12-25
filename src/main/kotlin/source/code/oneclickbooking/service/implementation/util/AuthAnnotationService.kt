package source.code.oneclickbooking.service.implementation.util

import org.springframework.stereotype.Service
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.EntityType
import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.repository.ImageRepository
import source.code.oneclickbooking.repository.ReviewRepository

@Service
class AuthAnnotationService(
    private val reviewRepository: ReviewRepository,
    private val bookingRepository: BookingRepository,
    private val imageRepository: ImageRepository,
) {
    fun isReviewOwner(reviewId: Int): Boolean {
        val review = reviewRepository.findById(reviewId)
            .orElseThrow { RecordNotFoundException(Review::class, reviewId) }

        return AuthorizationUtil.isOwnerOrAdmin(review.booking.user.id)
    }

    fun isBookingOwner(bookingId: Int): Boolean {
        val booking = bookingRepository.findById(bookingId)
            .orElseThrow { RecordNotFoundException(Booking::class, bookingId) }

        return AuthorizationUtil.isOwnerOrAdmin(booking.user.id)
    }

    fun isImageRelatedEntityAuthorOrAdmin(parentType: EntityType, parentId: Int): Boolean {
        return when (parentType) {
            EntityType.REVIEW -> isReviewOwner(parentId)
            EntityType.SERVICE_POINT -> AuthorizationUtil.isAdmin()
            EntityType.EMPLOYEE -> AuthorizationUtil.isAdmin()
        }
    }

    fun isImageRelatedEntityAuthorOrAdminById(id: Int): Boolean {
        val image = imageRepository.findById(id)
            .orElseThrow { RecordNotFoundException(Review::class, id) }

        return isImageRelatedEntityAuthorOrAdmin(image.parentType, image.parentId)
    }
}