package source.code.oneclickbooking.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import source.code.oneclickbooking.dto.response.TreatmentResponseDto
import source.code.oneclickbooking.service.declaration.TreatmentService

@RestController
@RequestMapping("/api/treatments")
class TreatmentController(
    private val service: TreatmentService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ResponseEntity<TreatmentResponseDto> =
        ResponseEntity.ok(service.get(id))

    @GetMapping
    fun getAll(): ResponseEntity<List<TreatmentResponseDto>> =
        ResponseEntity.ok(service.getAll())

    @GetMapping("/service-point/{servicePointId}")
    fun getAllByServicePoint(
        @PathVariable servicePointId: Int
    ): ResponseEntity<List<TreatmentResponseDto>> =
        ResponseEntity.ok(service.getAll(servicePointId))
}