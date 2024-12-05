package source.code.oneclickbooking.mapper

import org.mapstruct.Mapper
import source.code.oneclickbooking.dto.response.ServicePointResponseDto
import source.code.oneclickbooking.model.ServicePoint

@Mapper(componentModel = "spring")
abstract class ServicePointMapper {
    abstract fun toResponseDto(servicePoint: ServicePoint) : ServicePointResponseDto
}