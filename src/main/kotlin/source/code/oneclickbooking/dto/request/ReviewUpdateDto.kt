package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.Size
import source.code.oneclickbooking.model.Review


data class ReviewUpdateDto (
    val rating: Int? = null,

    @field:Size(max = Review.MAX_TEXT_LENGTH)
    val text: String? = null,
)