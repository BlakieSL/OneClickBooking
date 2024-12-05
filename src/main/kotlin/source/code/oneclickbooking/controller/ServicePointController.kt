package source.code.oneclickbooking.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import source.code.oneclickbooking.dto.response.ServicePointResponseDto
import source.code.oneclickbooking.service.declaration.ServicePointService

@RestController
@RequestMapping("/api/service-points")
class ServicePointController(
    private val servicePointService: ServicePointService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) : ResponseEntity<ServicePointResponseDto> =
        ResponseEntity.ok(servicePointService.get(id))

    @GetMapping
    fun getAll() : ResponseEntity<List<ServicePointResponseDto>> =
        ResponseEntity.ok(servicePointService.getAll())
}