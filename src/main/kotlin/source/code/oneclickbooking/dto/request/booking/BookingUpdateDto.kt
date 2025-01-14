package source.code.oneclickbooking.dto.request.booking

import jakarta.validation.constraints.FutureOrPresent
import java.time.LocalDateTime

data class BookingUpdateDto (
    @field:FutureOrPresent
    val date: LocalDateTime? = null,
    val servicePointId: Int? = null,
    val employeeId: Int? = null,
    val treatmentId: Int? = null
)