package source.code.oneclickbooking.dto.response

data class ReviewResponseDto (
    val id: Int,
    val rating: Int,
    val text: String? = null,
    val bookingId: Int
)