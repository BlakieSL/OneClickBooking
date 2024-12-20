package source.code.oneclickbooking.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import source.code.oneclickbooking.dto.other.FilterDto

data class ScheduleRequestDto(
    @JsonProperty(required = true)
    @field:NotNull
    val filter: FilterDto,

    @JsonProperty(required = true)
    @field:NotNull
    val treatmentId: Int
)
