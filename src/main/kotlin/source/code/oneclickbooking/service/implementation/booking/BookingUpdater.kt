package source.code.oneclickbooking.service.implementation.booking

import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.service.declaration.util.TimeProviderService

@Service
class BookingUpdater(
    private val bookingRepository: BookingRepository,
    private val timeProviderService: TimeProviderService,
) {

    @Scheduled(fixedRate = 60000)
    @Transactional
    fun updateBookingStatuses() {
        bookingRepository.markExpiredBookingsAsCompleted(timeProviderService.getCurrentTime())
    }
}