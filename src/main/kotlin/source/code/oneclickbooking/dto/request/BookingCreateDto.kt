package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class BookingCreateDto (
    @field:NotNull
    @field:FutureOrPresent
    val date: LocalDateTime,

    @field:NotNull
    val userId: Int,

    @field:NotNull
    val servicePointId: Int,

    @field:NotNull
    val employeeId: Int,

    @field:NotNull
    val treatmentId: Int
)