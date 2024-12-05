package source.code.oneclickbooking.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import source.code.oneclickbooking.model.ServicePoint

class ServicePointMapperTest {
    private val servicePointMapper = Mappers.getMapper(ServicePointMapper::class.java)

    @Test
    fun `should map ServicePoint to ServicePointResponseDto`() {
        val servicePoint = ServicePoint.createDefault(id = 1)

        val responseDto = servicePointMapper.toResponseDto(servicePoint)

        assertEquals(1, responseDto.id)
        assertEquals(servicePoint.name, responseDto.name)
        assertEquals(servicePoint.location, responseDto.location)
        assertEquals(servicePoint.email, responseDto.email)
        assertEquals(servicePoint.phone, responseDto.phone)
    }
}