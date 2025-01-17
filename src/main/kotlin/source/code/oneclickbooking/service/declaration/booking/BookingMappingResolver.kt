package source.code.oneclickbooking.service.declaration.booking

import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.ServicePointDetails
import source.code.oneclickbooking.model.Employee
import source.code.oneclickbooking.model.ServicePoint
import source.code.oneclickbooking.model.Treatment
import source.code.oneclickbooking.model.User

interface BookingMappingResolver {
    fun resolveUser(id: Int): User
    fun resolveServicePoint(id: Int): ServicePoint
    fun resolveEmployee(id: Int): Employee
    fun resolveTreatment(id: Int): Treatment
    fun resolveServicePointDetails(id: Int): ServicePointDetails
    fun resolveEmployeeDetails(id: Int): EmployeeDetails
}