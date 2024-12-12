package source.code.oneclickbooking.service.declaration

import source.code.oneclickbooking.dto.response.EmployeeResponseDto

interface EmployeeService {
    fun get(id: Int): EmployeeResponseDto
    fun getAll(): List<EmployeeResponseDto>
    fun getAll(servicePointId: Int, treatmentId: Int): List<EmployeeResponseDto>
}