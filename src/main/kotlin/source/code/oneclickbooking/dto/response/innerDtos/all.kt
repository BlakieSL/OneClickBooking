package source.code.oneclickbooking.dto.response.innerDtos

data class UserDetails (
    val id: Int,
    val name: String
)

data class EmployeeDetails (
    val id: Int,
    val username: String
)

data class ServicePointResponseDto(
    val id: Int,
    val name: String,
    val location: String,
    val email: String,
    val phone: String,
)

data class ServicePointDetails (
    val id: Int,
    val name: String,
    val location: String,
)
