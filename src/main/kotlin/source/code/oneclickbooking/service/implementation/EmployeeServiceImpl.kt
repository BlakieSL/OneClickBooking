package source.code.oneclickbooking.service.implementation

import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.response.EmployeeResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.EmployeeMapper
import source.code.oneclickbooking.model.Employee
import source.code.oneclickbooking.repository.EmployeeRepository
import source.code.oneclickbooking.service.declaration.EmployeeService
import source.code.oneclickbooking.specification.EmployeeSpecification
import source.code.oneclickbooking.specification.SpecificationBuilder
import source.code.oneclickbooking.specification.SpecificationFactory

@Service
class EmployeeServiceImpl(
    private val mapper: EmployeeMapper,
    private val repository: EmployeeRepository,
): EmployeeService {
    override fun get(id: Int): EmployeeResponseDto {
        return find(id).let { mapper.toResponseDto(it) }
    }

    override fun getAll(): List<EmployeeResponseDto> {
        return repository.findAll().map { mapper.toResponseDto(it) }
    }

    override fun getFiltered(filter: FilterDto): List<EmployeeResponseDto> {
        val employeeFactory = SpecificationFactory { EmployeeSpecification(it) }
        val specificationBuilder = SpecificationBuilder(filter, employeeFactory)
        val specification = specificationBuilder.build()
        return repository.findAll(specification).map { mapper.toResponseDto(it) }
    }

    private fun find(id: Int): Employee {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException(Employee::class, id)
        }
    }
}