package source.code.oneclickbooking.service.declaration

import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.ScheduleRequestDto
import source.code.oneclickbooking.dto.response.ScheduleResponseDto

interface ScheduleService {
    fun getSchedule(filter: ScheduleRequestDto): ScheduleResponseDto
}