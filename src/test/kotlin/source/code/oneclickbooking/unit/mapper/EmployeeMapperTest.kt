package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import source.code.oneclickbooking.mapper.EmployeeMapper
import source.code.oneclickbooking.model.Employee

@ExtendWith(MockitoExtension::class)
class EmployeeMapperTest {

    @InjectMocks
    private lateinit var employeeMapper: EmployeeMapper

    private lateinit var employee: Employee

    @BeforeEach
    fun setUp() {
        employee = Employee.createDefault(id = 1)
    }

    @Test
    fun `should map Employee to EmployeeResponseDto`() {
        val result = employeeMapper.toResponseDto(employee)

        assertEquals(employee.id, result.id)
        assertEquals(employee.username, result.username)
        assertEquals(employee.description, result.description)
    }
}