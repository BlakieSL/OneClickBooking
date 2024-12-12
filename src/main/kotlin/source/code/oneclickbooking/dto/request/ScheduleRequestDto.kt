package source.code.oneclickbooking.dto.request

import source.code.oneclickbooking.dto.other.FilterDto

data class ScheduleRequestDto(
    val filter: FilterDto,
    val treatmentId: Int
)
