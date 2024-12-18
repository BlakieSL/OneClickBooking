package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import source.code.oneclickbooking.dto.response.ServicePointResponseDto
import source.code.oneclickbooking.mapper.ServicePointMapper
import source.code.oneclickbooking.model.ServicePoint

class ServicePointMapperTest {
    private val servicePointMapper = ServicePointMapper()

    @Test
    fun `should map ServicePoint to ServicePointResponseDto`() {
        val servicePoint = ServicePoint.createDefault()

        val responseDto: ServicePointResponseDto = servicePointMapper.toResponseDto(servicePoint)

        assertEquals(1, responseDto.id)
        assertEquals(servicePoint.name, responseDto.name)
        assertEquals(servicePoint.location, responseDto.location)
        assertEquals(servicePoint.email, responseDto.email)
        assertEquals(servicePoint.phone, responseDto.phone)
    }
}
