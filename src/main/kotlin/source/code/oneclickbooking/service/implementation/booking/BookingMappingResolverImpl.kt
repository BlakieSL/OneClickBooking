package source.code.oneclickbooking.service.implementation.booking

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.ServicePointDetails
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.Employee
import source.code.oneclickbooking.model.ServicePoint
import source.code.oneclickbooking.model.Treatment
import source.code.oneclickbooking.model.User
import source.code.oneclickbooking.repository.EmployeeRepository
import source.code.oneclickbooking.repository.ServicePointRepository
import source.code.oneclickbooking.repository.TreatmentRepository
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.declaration.booking.BookingMappingResolver

@Component
class BookingMappingResolverImpl(
    private val userRepository: UserRepository,
    private val servicePointRepository: ServicePointRepository,
    private val employeeRepository: EmployeeRepository,
    private val treatmentRepository: TreatmentRepository
): BookingMappingResolver{
    override fun resolveUser(id: Int): User {
        return userRepository.findById(id).orElseThrow {
            RecordNotFoundException(User::class, id)
        }
    }

    override fun resolveServicePoint(id: Int): ServicePoint {
        return servicePointRepository.findById(id).orElseThrow {
            RecordNotFoundException(ServicePoint::class, id)
        }
    }

    override fun resolveEmployee(id: Int): Employee{
        return employeeRepository.findById(id).orElseThrow {
            RecordNotFoundException(Employee::class, id)
        }
    }

    override fun resolveTreatment(id: Int): Treatment {
        return treatmentRepository.findById(id).orElseThrow {
            RecordNotFoundException(Treatment::class, id)
        }
    }

    override fun resolveServicePointDetails(id: Int): ServicePointDetails {
        return servicePointRepository.findById(id).orElseThrow {
            RecordNotFoundException(ServicePoint::class, id)
        }.let {
            ServicePointDetails(
                id = it.id!!,
                name = it.name,
                location = it.location
            )
        }
    }

    override fun resolveEmployeeDetails(id: Int): EmployeeDetails {
        return employeeRepository.findById(id).orElseThrow {
            RecordNotFoundException(Employee::class, id)
        }.let {
            EmployeeDetails(
                id = it.id!!,
                username = it.username
            )
        }
    }
}