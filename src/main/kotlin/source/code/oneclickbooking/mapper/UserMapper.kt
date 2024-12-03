package source.code.oneclickbooking.mapper

import org.mapstruct.*
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.model.User

@Mapper(componentModel = "spring")
abstract class UserMapper {
    abstract fun toResponseDto(user: User) : UserResponseDto

    @Mapping(target = "password", expression = "java(hashedPassword)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    abstract fun toEntity(user: UserCreateDto, @Context hashedPassword: String) : User

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "password", ignore = true)
    abstract fun update(@MappingTarget user: User, request: UserUpdateDto)

}