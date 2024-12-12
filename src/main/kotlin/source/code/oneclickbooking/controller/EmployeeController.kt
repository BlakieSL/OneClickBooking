package source.code.oneclickbooking.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import source.code.oneclickbooking.dto.response.EmployeeResponseDto
import source.code.oneclickbooking.service.declaration.EmployeeService

@RestController
@RequestMapping("/api/employees")
class EmployeeController(
    private val employeeService: EmployeeService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ResponseEntity<EmployeeResponseDto> =
        ResponseEntity.ok(employeeService.get(id))

    @GetMapping
    fun getAll(): ResponseEntity<List<EmployeeResponseDto>> =
        ResponseEntity.ok(employeeService.getAll())

    @GetMapping("/service-point/{servicePointId}/treatment/{treatmentId}")
    fun getAll(
        @PathVariable servicePointId: Int,
        @PathVariable treatmentId: Int
    ): ResponseEntity<List<EmployeeResponseDto>> =
        ResponseEntity.ok(employeeService.getAll(servicePointId, treatmentId))
}