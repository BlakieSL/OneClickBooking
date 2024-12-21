package source.code.oneclickbooking.service.implementation.schdule

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.EmployeeAvailability
import source.code.oneclickbooking.service.declaration.schedule.ScheduleUtilsService
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class ScheduleUtilsServiceImpl : ScheduleUtilsService {
    override fun generatePotentialSlots(
        date: LocalDate,
        availability: EmployeeAvailability,
        incrementMinutes: Int,
        treatmentDuration: Int
    ): List<LocalDateTime> {
        val startDateTime = date.atTime(availability.startTime)
        val endDateTime = date.atTime(availability.endTime)

        val slots = mutableListOf<LocalDateTime>()
        var current = startDateTime

        while (current.plusMinutes(treatmentDuration.toLong()) <= endDateTime) {
            slots.add(current)
            current = current.plusMinutes(incrementMinutes.toLong())
        }

        return slots
    }

    override fun findTakenSlots(
        allPotentialSlots: List<LocalDateTime>,
        bookings: List<Booking>,
        treatmentDuration: Int
    ): Set<LocalDateTime> {
        val takenSlots = mutableSetOf<LocalDateTime>()

        for (booking in bookings) {
            val bookingStart = booking.date
            val bookingDuration = booking.treatment!!.duration
            val bookingEnd = bookingStart.plusMinutes(bookingDuration.toLong())

            val overlappingSlots = allPotentialSlots.filter {
                val slotEnd = it.plusMinutes(treatmentDuration.toLong())
                it < bookingEnd && slotEnd > bookingStart
            }

            takenSlots.addAll(overlappingSlots)
        }

        return takenSlots
    }
}