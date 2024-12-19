package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.NotNull
import source.code.oneclickbooking.dto.other.FilterDto

data class ScheduleRequestDto(
    @field:NotNull
    val filter: FilterDto,
    @field:NotNull
    val treatmentId: Int
)
