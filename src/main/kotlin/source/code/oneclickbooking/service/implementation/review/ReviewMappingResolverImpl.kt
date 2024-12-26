package source.code.oneclickbooking.service.implementation.review

import org.springframework.stereotype.Component
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.Employee
import source.code.oneclickbooking.model.User
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.service.declaration.review.ReviewMappingResolver

@Component
class ReviewMappingResolverImpl(
    private val bookingRepository: BookingRepository
): ReviewMappingResolver {
    override fun resolveBooking(id: Int): Booking {
        return bookingRepository.findById(id).orElseThrow {
            RecordNotFoundException(Booking::class, id)
        }
    }
}