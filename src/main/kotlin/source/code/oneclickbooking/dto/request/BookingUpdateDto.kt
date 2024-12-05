package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.FutureOrPresent
import java.time.LocalDateTime

data class BookingUpdateDto (
    @field:FutureOrPresent
    var date: LocalDateTime? = null,
    var userId: Int? = null,
    var servicePointId: Int? = null,
    var employeeId: Int? = null,
    var treatmentId: Int? = null
)