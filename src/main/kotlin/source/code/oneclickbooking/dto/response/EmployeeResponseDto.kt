package source.code.oneclickbooking.dto.response

data class EmployeeResponseDto (
    val id: Int,
    val username: String,
    val description: String? = null,
)