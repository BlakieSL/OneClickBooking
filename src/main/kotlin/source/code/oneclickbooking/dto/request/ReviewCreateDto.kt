package source.code.oneclickbooking.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull


data class ReviewCreateDto (
    @JsonProperty(required = true)
    @field:NotNull
    val rating: Int,

    val text: String? = null,

    @JsonProperty(required = true)
    @field:NotNull
    val bookingId: Int
)