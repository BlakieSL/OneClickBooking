package source.code.oneclickbooking.service.implementation.schedule

import org.springframework.stereotype.Component
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
        val now = LocalDateTime.now()
        val startDateTime = if (date.isEqual(now.toLocalDate())) {
            maxOf(
                roundUpToNearestIncrement(now, incrementMinutes),
                date.atTime(availability.startTime)
            )
        } else {
            date.atTime(availability.startTime)
        }
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
            val bookingDuration = booking.treatment!!.duration
            val bookingStart = booking.date
            val bookingEnd = bookingStart.plusMinutes(bookingDuration.toLong())

            val overlappingSlots = allPotentialSlots.filter { slotStart ->
                val slotEnd = slotStart.plusMinutes(treatmentDuration.toLong())
                slotStart < bookingEnd && bookingStart < slotEnd
            }

            takenSlots.addAll(overlappingSlots)
        }

        return takenSlots
    }

    private fun roundUpToNearestIncrement(
        time: LocalDateTime,
        incrementMinutes: Int
    ): LocalDateTime {
        val minutes = time.minute
        val remainder = minutes % incrementMinutes
        val minutesToAdd = if (remainder == 0) {
            0
        } else {
            incrementMinutes - remainder
        }

        return time.plusMinutes(minutesToAdd.toLong())
            .withSecond(0)
            .withNano(0)
    }
}