package source.code.oneclickbooking.dto.response

import java.time.LocalDateTime

data class ScheduleResponseDto (
    val freeSlots: List<LocalDateTime>
)