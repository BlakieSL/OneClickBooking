package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.NotNull


data class ReviewCreateDto (
    @field:NotNull
    val rating: Int,

    val text: String? = null,

    @field:NotNull
    val bookingId: Int
)