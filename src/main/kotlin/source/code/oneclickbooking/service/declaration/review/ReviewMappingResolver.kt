package source.code.oneclickbooking.service.declaration.review

import source.code.oneclickbooking.model.Booking

interface ReviewMappingResolver {
    fun resolveBooking(id: Int): Booking
}