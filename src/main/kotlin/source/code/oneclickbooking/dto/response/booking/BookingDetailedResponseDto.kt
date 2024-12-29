package source.code.oneclickbooking.dto.response.booking

import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.ServicePointDetails
import java.time.LocalDateTime

data class BookingDetailedResponseDto (
    val id: Int,
    val date: LocalDateTime,
    val userId: Int,
    val servicePoint: ServicePointDetails,
    val employee: EmployeeDetails? = null,
    val treatmentId: Int? = null,
    val reviewId: Int? = null
)

