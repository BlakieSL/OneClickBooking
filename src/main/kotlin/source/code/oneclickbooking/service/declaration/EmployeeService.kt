package source.code.oneclickbooking.service.declaration

import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.response.EmployeeResponseDto

interface EmployeeService {
    fun get(id: Int): EmployeeResponseDto
    fun getAll(): List<EmployeeResponseDto>
    fun getFiltered(filter: FilterDto): List<EmployeeResponseDto>
}