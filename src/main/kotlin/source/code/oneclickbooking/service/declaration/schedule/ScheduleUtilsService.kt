package source.code.oneclickbooking.service.declaration.schedule

import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.EmployeeAvailability
import java.time.LocalDate
import java.time.LocalDateTime

interface ScheduleUtilsService {
    fun generatePotentialSlots(
        date: LocalDate,
        availability: EmployeeAvailability,
        incrementMinutes: Int,
        treatmentDuration: Int
    ): List<LocalDateTime>

    fun findTakenSlots(
        allPotentialSlots: List<LocalDateTime>,
        bookings: List<Booking>,
        treatmentDuration: Int
    ): Set<LocalDateTime>
}