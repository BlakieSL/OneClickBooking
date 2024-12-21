package source.code.oneclickbooking.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import source.code.oneclickbooking.dto.request.ScheduleRequestDto
import source.code.oneclickbooking.dto.response.ScheduleResponseDto
import source.code.oneclickbooking.service.declaration.schedule.ScheduleService

@RestController
@RequestMapping("/api/schedule")
class ScheduleController(private val scheduleService: ScheduleService) {
    @PostMapping
    fun getSchedule(
        @Valid @RequestBody request: ScheduleRequestDto
    ): ResponseEntity<ScheduleResponseDto> {
        return ResponseEntity.ok(scheduleService.getSchedule(request))
    }
}