package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import source.code.oneclickbooking.model.Booking
import java.time.LocalDate
import java.time.LocalDateTime

interface BookingRepository : JpaRepository<Booking, Int>, JpaSpecificationExecutor<Booking > {
    fun findBookingByServicePointIdAndDateGreaterThanEqual(
        servicePointId: Int,
        date: LocalDateTime
    ): List<Booking>
}