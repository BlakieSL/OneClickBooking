package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.other.UserCredentialsDto
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.model.Role
import source.code.oneclickbooking.model.User

@Component
class UserMapper {
    fun toResponseDto(user: User): UserResponseDto {
        return UserResponseDto(
            id = user.id!!,
            name = user.name,
            surname = user.surname,
            email = user.email
        )
    }

    fun toEntity(dto: UserCreateDto, hashedPassword: String): User {
        return User(
            name = dto.name,
            surname = dto.surname,
            email = dto.email,
            password = hashedPassword
        )
    }

    fun update(user: User, dto: UserUpdateDto) {
        dto.name?.let { user.name = it }
        dto.surname?.let { user.surname = it }
    }

    fun toCredentialsDto(user: User): UserCredentialsDto {
        return UserCredentialsDto(
            email = user.email,
            password = user.password,
            roles = rolesToRolesNames(user.roles)
        )
    }

    private fun rolesToRolesNames(roles: Set<Role>): Array<String> {
        return roles.map { it.name.name }.toTypedArray()
    }
}
