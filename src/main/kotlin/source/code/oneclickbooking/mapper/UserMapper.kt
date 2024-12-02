package source.code.oneclickbooking.mapper

import org.mapstruct.*
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.model.User

@Mapper(componentModel = "spring")
abstract class UserMapper {
    abstract fun toResponseDto(user: User) : UserResponseDto
    abstract fun toEntity(user: UserCreateDto) : User
    abstract fun update(@MappingTarget user: User, request: UserUpdateDto)
}