package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.Employee

interface EmployeeRepository : JpaRepository<Employee, Int> {
}