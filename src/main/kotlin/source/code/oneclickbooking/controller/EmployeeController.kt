package source.code.oneclickbooking.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import source.code.oneclickbooking.dto.other.FilterDto
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

    @PostMapping("/filtered")
    fun getFiltered(@Valid @RequestBody filter: FilterDto): ResponseEntity<List<EmployeeResponseDto>> =
        ResponseEntity.ok(employeeService.getFiltered(filter))
}