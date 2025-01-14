package source.code.oneclickbooking.dto.request.booking

import jakarta.validation.constraints.NotNull
import source.code.oneclickbooking.model.BookingStatus

data class BookingUpdateStatusDto(
    @field:NotNull
    val status: BookingStatus
)
