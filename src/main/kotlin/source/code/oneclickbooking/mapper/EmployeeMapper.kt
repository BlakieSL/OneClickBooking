package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.response.EmployeeResponseDto
import source.code.oneclickbooking.model.Employee

@Component
class EmployeeMapper {
    fun toResponseDto(employee: Employee): EmployeeResponseDto {
        return EmployeeResponseDto(
            id = employee.id!!,
            username = employee.username,
            description = employee.description,
        )
    }
}