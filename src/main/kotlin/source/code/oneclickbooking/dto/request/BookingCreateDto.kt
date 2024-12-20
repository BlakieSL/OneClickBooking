package source.code.oneclickbooking.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class BookingCreateDto (
    @JsonProperty(required = true)
    @field:NotNull
    @field:FutureOrPresent
    val date: LocalDateTime,

    @JsonProperty(required = true)
    @field:NotNull
    val userId: Int,

    @JsonProperty(required = true)
    @field:NotNull
    val servicePointId: Int,

    @JsonProperty(required = true)
    @field:NotNull
    val employeeId: Int,

    @JsonProperty(required = true)
    @field:NotNull
    val treatmentId: Int
)