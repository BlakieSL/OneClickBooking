package source.code.oneclickbooking.service.declaration.employee

import source.code.oneclickbooking.dto.response.EmployeeResponseDto

interface EmployeeService {
    fun get(id: Int): EmployeeResponseDto
    fun getAll(): List<EmployeeResponseDto>
}