package source.code.oneclickbooking.service.declaration.schedule

import source.code.oneclickbooking.dto.request.ScheduleRequestDto
import source.code.oneclickbooking.dto.response.ScheduleResponseDto

interface ScheduleService {
    fun getSchedule(request: ScheduleRequestDto): ScheduleResponseDto
}