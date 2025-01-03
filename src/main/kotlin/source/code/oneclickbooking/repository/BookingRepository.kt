package source.code.oneclickbooking.repository

import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import source.code.oneclickbooking.model.Booking
import java.awt.print.Book
import java.time.LocalDateTime

interface BookingRepository : JpaRepository<Booking, Int>, JpaSpecificationExecutor<Booking > {
    @Query(
        """
    SELECT b FROM Booking b 
    WHERE b.servicePoint.id = :servicePointId 
      AND b.employee.id = :employeeId 
      AND DATE(b.date) = DATE(:date)
    """
    )
    fun findByServicePointIdAndEmployeeIdAndDate(
        servicePointId: Int,
        employeeId: Int,
        date: LocalDateTime
    ): List<Booking>

    @EntityGraph(attributePaths = ["servicePoint", "employee", "treatment", "user", "review"])
    override fun findAll(spec: Specification<Booking>?): List<Booking>
}