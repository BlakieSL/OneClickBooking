package source.code.oneclickbooking.service.booking

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import source.code.oneclickbooking.exception.RecordNotFoundException
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
    fun `should return null when id is null for user`() {
        val result = resolver.resolveUser(null)

        assertNull(result)
    }

    @Test
    fun `should return null when id is null for service point`() {
        val result = resolver.resolveServicePoint(null)

        assertNull(result)
    }

    @Test
    fun `should return null when id is null for employee`() {
        val result = resolver.resolveEmployee(null)

        assertNull(result)
    }

    @Test
    fun `should return null when id is null for treatment`() {
        val result = resolver.resolveTreatment(null)

        assertNull(result)
    }
}