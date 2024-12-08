package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.response.ServicePointResponseDto
import source.code.oneclickbooking.model.ServicePoint

@Component
class ServicePointMapper {
    fun toResponseDto(servicePoint: ServicePoint): ServicePointResponseDto {
        return ServicePointResponseDto(
            id = servicePoint.id!!,
            name = servicePoint.name,
            location = servicePoint.location,
            email = servicePoint.email,
            phone = servicePoint.phone
        )
    }
}
