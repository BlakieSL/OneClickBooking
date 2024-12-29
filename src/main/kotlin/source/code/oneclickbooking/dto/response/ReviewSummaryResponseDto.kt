package source.code.oneclickbooking.dto.response

class ReviewSummaryResponseDto (
    val reviews: List<ReviewResponseDto>,
    val averageRating: Double,
    val totalReviews: Int,
)