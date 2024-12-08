package source.code.oneclickbooking.service.implementation.booking

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
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
    override fun resolveUser(id: Int?): User? {
        return id?.let {
            userRepository.findById(it).orElseThrow {
                RecordNotFoundException(User::class, it)
            }
        }
    }

    override fun resolveServicePoint(id: Int?): ServicePoint? {
        return id?.let {
            servicePointRepository.findById(it).orElseThrow {
                RecordNotFoundException(ServicePoint::class, it)
            }
        }
    }

    override fun resolveEmployee(id: Int?): Employee? {
        return id?.let {
            employeeRepository.findById(it).orElseThrow {
                RecordNotFoundException(Employee::class, it)
            }
        }
    }

    override fun resolveTreatment(id: Int?): Treatment? {
        return id?.let {
            treatmentRepository.findById(it).orElseThrow {
                RecordNotFoundException(Treatment::class, it)
            }
        }
    }
}