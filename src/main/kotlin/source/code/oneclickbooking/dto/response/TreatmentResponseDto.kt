package source.code.oneclickbooking.dto.response

data class TreatmentResponseDto (
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val duration: Int
)