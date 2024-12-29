package source.code.oneclickbooking.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import source.code.oneclickbooking.model.Review


data class ReviewCreateDto (
    @JsonProperty(required = true)
    @field:NotNull
    val rating: Int,

    @field:Size(max = Review.MAX_TEXT_LENGTH)
    val text: String? = null,

    @JsonProperty(required = true)
    @field:NotNull
    val bookingId: Int
)