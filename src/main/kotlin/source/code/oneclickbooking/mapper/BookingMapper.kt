package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.service.declaration.booking.BookingMappingResolver

@Component
class BookingMapper(
    private val resolver: BookingMappingResolver
) {
    fun toResponseDto(booking: Booking): BookingResponseDto {
        return BookingResponseDto(
            id = booking.id!!,
            date = booking.date,
            userId = booking.user.id!!,
            servicePointId = booking.servicePoint.id!!,
            employeeId = booking.employee?.id,
            treatmentId = booking.treatment?.id,
            reviewId = booking.review?.id
        )
    }

    fun toEntity(dto: BookingCreateDto): Booking {
        return Booking.of(
            date = dto.date,
            user = resolver.resolveUser(dto.userId)!!,
            servicePoint = resolver.resolveServicePoint(dto.servicePointId)!!,
            employee = resolver.resolveEmployee(dto.employeeId)!!,
            treatment = resolver.resolveTreatment(dto.treatmentId)!!
        )
    }

    fun update(booking: Booking, dto: BookingUpdateDto) {
        dto.date?.let { booking.date = it }
        dto.servicePointId?.let { booking.servicePoint = resolver.resolveServicePoint(it)!! }
        dto.employeeId?.let { booking.employee = resolver.resolveEmployee(it)!! }
        dto.treatmentId?.let { booking.treatment = resolver.resolveTreatment(it)!! }
    }
}
