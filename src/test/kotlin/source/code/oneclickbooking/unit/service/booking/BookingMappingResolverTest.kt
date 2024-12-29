package source.code.oneclickbooking.unit.service.booking

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.lenient
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.context.MessageSource
import source.code.oneclickbooking.dto.response.innerDtos.EmployeeDetails
import source.code.oneclickbooking.dto.response.innerDtos.ServicePointDetails
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.helper.MessageResolver
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.*
import source.code.oneclickbooking.service.implementation.booking.BookingMappingResolverImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookingMappingResolverTest {
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var servicePointRepository: ServicePointRepository
    @Mock
    private lateinit var employeeRepository: EmployeeRepository
    @Mock
    private lateinit var treatmentRepository: TreatmentRepository
    @InjectMocks
    private lateinit var resolver: BookingMappingResolverImpl

    private lateinit var user: User
    private lateinit var servicePoint: ServicePoint
    private lateinit var employee: Employee
    private lateinit var treatment: Treatment

    @BeforeEach
    fun setUp() {

        user = User.createDefault()
        servicePoint = ServicePoint.createDefault()
        employee = Employee.createDefault()
        treatment = Treatment.createDefault()
    }

    @Test
    fun `should resolve user by id`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))

        val result = resolver.resolveUser(1)

        assertEquals(user, result)
    }

    @Test
    fun `should throw exception when user is not found`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            resolver.resolveUser(1)
        }
    }

    @Test
    fun `should resolve service point by id`() {
        whenever(servicePointRepository.findById(1)).thenReturn(Optional.of(servicePoint))

        val result = resolver.resolveServicePoint(1)

        assertEquals(servicePoint, result)
    }

    @Test
    fun `should throw exception when service point is not found`() {
        whenever(servicePointRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            resolver.resolveServicePoint(1)
        }
    }

    @Test
    fun `should resolve employee by id`() {
        whenever(employeeRepository.findById(1)).thenReturn(Optional.of(employee))

        val result = resolver.resolveEmployee(1)

        assertEquals(employee, result)
    }

    @Test
    fun `should throw exception when employee is not found`() {
        whenever(employeeRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            resolver.resolveEmployee(1)
        }
    }

    @Test
    fun `should resolve treatment by id`() {
        whenever(treatmentRepository.findById(1)).thenReturn(Optional.of(treatment))

        val result = resolver.resolveTreatment(1)

        assertEquals(treatment, result)
    }

    @Test
    fun `should throw exception when treatment is not found`() {
        whenever(treatmentRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            resolver.resolveTreatment(1)
        }
    }

    @Test
    fun `should resolve service point details by id`() {
        val servicePointDetails = ServicePointDetails(
            id = 1,
            name = "Test Service Point",
            location = "Test Location"
        )

        val servicePoint = ServicePoint(
            id = 1,
            name = servicePointDetails.name,
            location = servicePointDetails.location,
            email = "test@example.com",
            phone = "1234567890"
        )

        whenever(servicePointRepository.findById(1)).thenReturn(Optional.of(servicePoint))

        val result = resolver.resolveServicePointDetails(1)

        assertEquals(servicePointDetails, result)
    }

    @Test
    fun `should throw exception when service point details are not found`() {
        whenever(servicePointRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            resolver.resolveServicePointDetails(1)
        }
    }

    @Test
    fun `should resolve employee details by id`() {
        val employeeDetails = EmployeeDetails(
            id = 1,
            username = "Test Employee"
        )

        val employee = Employee(
            id = 1,
            username = employeeDetails.username,
            description = "Test Employee Description"
        )

        whenever(employeeRepository.findById(1)).thenReturn(Optional.of(employee))

        val result = resolver.resolveEmployeeDetails(1)

        assertEquals(employeeDetails, result)
    }

    @Test
    fun `should throw exception when employee details are not found`() {
        whenever(employeeRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            resolver.resolveEmployeeDetails(1)
        }
    }


}