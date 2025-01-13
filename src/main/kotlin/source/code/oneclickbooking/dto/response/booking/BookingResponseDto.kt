package source.code.oneclickbooking.dto.response.booking

import source.code.oneclickbooking.model.BookingStatus
import java.time.LocalDateTime

data class BookingResponseDto (
    val id: Int,
    val date: LocalDateTime,
    val status: BookingStatus,
    val userId: Int,
    val servicePointId: Int,
    val employeeId: Int? = null,
    val treatmentId: Int? = null,
    val reviewId: Int? = null
)
