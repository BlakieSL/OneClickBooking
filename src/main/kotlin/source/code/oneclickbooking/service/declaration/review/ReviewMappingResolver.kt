package source.code.oneclickbooking.service.declaration.review

import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.Employee
import source.code.oneclickbooking.model.User

interface ReviewMappingResolver {
    fun resolveBooking(id: Int): Booking
}