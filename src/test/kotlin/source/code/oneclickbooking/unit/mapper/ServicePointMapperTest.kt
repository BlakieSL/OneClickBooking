package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import source.code.oneclickbooking.dto.response.ServicePointResponseDto
import source.code.oneclickbooking.mapper.ServicePointMapper
import source.code.oneclickbooking.model.ServicePoint

@ExtendWith(MockitoExtension::class)
class ServicePointMapperTest {

    @InjectMocks
    private lateinit var servicePointMapper: ServicePointMapper

    private lateinit var servicePoint: ServicePoint

    @BeforeEach
    fun setUp() {
        servicePoint = ServicePoint.createDefault(id = 1)
    }

    @Test
    fun `should map ServicePoint to ServicePointResponseDto`() {
        val responseDto: ServicePointResponseDto = servicePointMapper.toResponseDto(servicePoint)

        assertEquals(1, responseDto.id)
        assertEquals(servicePoint.name, responseDto.name)
        assertEquals(servicePoint.location, responseDto.location)
        assertEquals(servicePoint.email, responseDto.email)
        assertEquals(servicePoint.phone, responseDto.phone)
    }
}
