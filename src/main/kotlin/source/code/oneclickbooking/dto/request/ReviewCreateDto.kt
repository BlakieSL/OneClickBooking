package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.NotNull


data class ReviewCreateDto (
    @field:NotNull
    var rating: Int,

    var text: String? = null,

    @field:NotNull
    var bookingId: Int
)