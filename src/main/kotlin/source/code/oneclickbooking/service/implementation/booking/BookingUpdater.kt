package source.code.oneclickbooking.service.implementation.booking

import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import source.code.oneclickbooking.model.BookingStatus
import source.code.oneclickbooking.repository.BookingRepository
import java.time.LocalDateTime.now

@Service
class BookingUpdater(
    private val bookingRepository: BookingRepository
) {


}