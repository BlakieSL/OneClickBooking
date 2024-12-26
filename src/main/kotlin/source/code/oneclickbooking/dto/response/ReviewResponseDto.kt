package source.code.oneclickbooking.dto.response

import java.time.LocalDate

data class ReviewResponseDto (
    val id: Int,
    val rating: Int,
    val date: LocalDate,
    val text: String? = null,
    val bookingId: Int,

    val userId: Int,
    val employeeId: Int? = null,
)