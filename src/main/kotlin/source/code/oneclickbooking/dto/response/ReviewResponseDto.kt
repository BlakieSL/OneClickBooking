

package source.code.oneclickbooking.dto.response


import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.UserDetails
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

