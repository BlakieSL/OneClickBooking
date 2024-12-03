package source.code.oneclickbooking.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.model.User

class UserMapperTest {
    private val userMapper = Mappers.getMapper(UserMapper::class.java)

    @Test
    fun `should map UserCreateDto to User with hashed password`() {
        val userCreateDto = UserCreateDto.createDefault()
        val hashedPassword = "HashedPassword1!"

        val user = userMapper.toEntity(userCreateDto, hashedPassword)

        assertEquals("John", user.name)
        assertEquals("Doe", user.surname)
        assertEquals("email@gmail.com", user.email)
        assertEquals(hashedPassword, user.password)
    }

    @Test
    fun `should map User to UserResponseDto`() {
        val user = User.createDefault(1)

        val responseDto = userMapper.toResponseDto(user)

        assertEquals(1, responseDto.id)
        assertEquals("John", responseDto.name)
        assertEquals("Doe", responseDto.surname)
        assertEquals("john.doe@example.com", responseDto.email)
    }

    @Test
    fun `should update User fields from UserUpdateDto`() {
        val user = User.createDefault(1)
        val updateDto = UserUpdateDto(
            "UpdatedName",
            "UpdatedSurname",
            "updated.email@example.com"
        )

        userMapper.update(user, updateDto)

        assertEquals("UpdatedName", user.name)
        assertEquals("UpdatedSurname", user.surname)
        assertEquals("updated.email@example.com", user.email)
        assertEquals("hashed_password", user.password)
    }
}