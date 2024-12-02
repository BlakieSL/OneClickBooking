package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.EmployeeAvailability

interface EmployeeAvailabilityRepository : JpaRepository<EmployeeAvailability, Int> {
}