package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.booking.BookingResponseDto
import source.code.oneclickbooking.dto.response.booking.BookingDetailedResponseDto
import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.ServicePointDetails
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.service.declaration.booking.BookingMappingResolver

@Component
class BookingMapper(
    private val resolver: BookingMappingResolver
) {
    fun toResponseDto(booking: Booking): BookingResponseDto {
        return BookingResponseDto(
            id = booking.id!!,
            date = booking.date,
            status = booking.status,
            userId = booking.user.id!!,
            servicePointId = booking.servicePoint.id!!,
            employeeId = booking.employee?.id,
            treatmentId = booking.treatment?.id,
            reviewId = booking.review?.id
        )
    }

    fun toEntity(dto: BookingCreateDto, userId: Int): Booking {
        return Booking.of(
            date = dto.date,
            user = resolver.resolveUser(userId),
            servicePoint = resolver.resolveServicePoint(dto.servicePointId),
            employee = resolver.resolveEmployee(dto.employeeId),
            treatment = resolver.resolveTreatment(dto.treatmentId)
        )
    }

    fun update(booking: Booking, dto: BookingUpdateDto) {
        dto.date?.let { booking.date = it }
        dto.servicePointId?.let { booking.servicePoint = resolver.resolveServicePoint(it) }
        dto.employeeId?.let { booking.employee = resolver.resolveEmployee(it) }
        dto.treatmentId?.let { booking.treatment = resolver.resolveTreatment(it) }
    }

    fun toDetailedResponseDto(booking: Booking): BookingDetailedResponseDto {
        return BookingDetailedResponseDto(
            id = booking.id!!,
            date = booking.date,
            status = booking.status,
            userId = booking.user.id!!,
            servicePoint = ServicePointDetails(
                id = booking.servicePoint.id!!,
                name = booking.servicePoint.name,
                location = booking.servicePoint.location
            ),
            employee = booking.employee?.let {
                EmployeeDetails(
                    id = it.id!!,
                    username = it.username,
                )
            },
            treatmentId = booking.treatment?.id,
            reviewId = booking.review?.id
        )
    }
}
