package source.code.oneclickbooking.unit.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import source.code.oneclickbooking.dto.response.ServicePointResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.ServicePointMapper
import source.code.oneclickbooking.model.ServicePoint
import source.code.oneclickbooking.repository.ServicePointRepository
import source.code.oneclickbooking.service.implementation.ServicePointServiceImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class ServicePointServiceTest {
    @Mock
    private lateinit var repository: ServicePointRepository
    @Mock
    private lateinit var mapper: ServicePointMapper
    @InjectMocks
    private lateinit var servicePointService: ServicePointServiceImpl

    private lateinit var servicePoint: ServicePoint
    private lateinit var servicePointResponseDto: ServicePointResponseDto

    @BeforeEach
    fun setUp() {
        servicePoint = ServicePoint.createDefault()
        servicePointResponseDto = ServicePointResponseDto(
            id = 1,
            name = servicePoint.name,
            location = servicePoint.location,
            email = servicePoint.email,
            phone = servicePoint.phone
        )
    }

    @Test
    fun `should get service point by id`() {
        whenever(repository.findById(1)).thenReturn(Optional.of(servicePoint))
        whenever(mapper.toResponseDto(servicePoint)).thenReturn(servicePointResponseDto)

        val result = servicePointService.get(1)

        assertEquals(servicePointResponseDto, result)
        verify(repository).findById(1)
        verify(mapper).toResponseDto(servicePoint)
    }

    @Test
    fun `should throw exception when service point is not found by id`() {
        whenever(repository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            servicePointService.get(1)
        }

        verify(repository).findById(1)
    }

    @Test
    fun `should get all service points`() {
        val servicePoints = listOf(servicePoint)
        val servicePointResponseDtos = listOf(servicePointResponseDto)
        whenever(repository.findAll()).thenReturn(servicePoints)
        whenever(mapper.toResponseDto(servicePoint)).thenReturn(servicePointResponseDto)

        val result = servicePointService.getAll()

        assertEquals(servicePointResponseDtos, result)
        verify(repository).findAll()
        verify(mapper).toResponseDto(servicePoint)
    }
}