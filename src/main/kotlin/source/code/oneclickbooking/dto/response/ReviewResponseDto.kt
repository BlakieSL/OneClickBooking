package source.code.oneclickbooking.dto.response

import java.time.LocalDate

data class ReviewResponseDto (
    val id: Int,
    val rating: Int,
    val date: LocalDate,
    val text: String? = null,
    val bookingId: Int,

    val user: UserDetails,
    val employee: EmployeeDetails? = null,
)

data class UserDetails (
    val id: Int,
    val name: String
)

data class EmployeeDetails (
    val id: Int,
    val username: String
)