package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.FutureOrPresent
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

data class BookingCreateDto (
    @field:NotNull
    @field:FutureOrPresent
    var date: LocalDateTime,

    @field:NotNull
    var userId: Int,

    @field:NotNull
    var servicePointId: Int,

    @field:NotNull
    var employeeId: Int,

    @field:NotNull
    var treatmentId: Int
)