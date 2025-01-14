package source.code.oneclickbooking.repository

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.*
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.BookingStatus
import java.time.LocalDateTime

interface BookingRepository : JpaRepository<Booking, Int>, JpaSpecificationExecutor<Booking > {
    @Query("""
    SELECT b FROM Booking b 
    WHERE b.servicePoint.id = :servicePointId 
      AND b.employee.id = :employeeId 
      AND DATE(b.date) = DATE(:date)
    """)
    fun findByServicePointIdAndEmployeeIdAndDate (
        servicePointId: Int,
        employeeId: Int,
        date: LocalDateTime
    ): List<Booking>

    @EntityGraph(attributePaths = ["servicePoint", "employee", "treatment", "user", "review"])
    override fun findAll(spec: Specification<Booking>?): List<Booking>

    fun findBookingsByStatus(status: BookingStatus): List<Booking>

    @Modifying
    @Query("""
        UPDATE Booking b SET b.status = 'COMPLETED'
        WHERE b.status = 'PENDING' AND b.date < :now
    """)
    fun markExpiredBookingsAsCompleted(now: LocalDateTime): Int
}