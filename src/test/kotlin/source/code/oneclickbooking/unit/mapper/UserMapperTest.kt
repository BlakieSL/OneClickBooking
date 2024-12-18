package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import source.code.oneclickbooking.dto.other.UserCredentialsDto
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.mapper.UserMapper
import source.code.oneclickbooking.model.Role
import source.code.oneclickbooking.model.RoleName
import source.code.oneclickbooking.model.User

class UserMapperTest {

    private val userMapper = UserMapper()

    @Test
    fun `should map UserCreateDto to User with hashed password`() {
        val userCreateDto = UserCreateDto.createDefault()
        val hashedPassword = "HashedPassword1!"

        val user = userMapper.toEntity(userCreateDto, hashedPassword)

        assertEquals("John", user.name)
        assertEquals("Doe", user.surname)
        assertEquals("email@gmail.com", user.email)
        assertEquals(hashedPassword, user.password)
        assertEquals(0, user.roles.size)
        assertEquals(0, user.bookings.size)
    }

    @Test
    fun `should map User to UserResponseDto`() {
        val user = User.createDefault(id = 1)

        val responseDto: UserResponseDto = userMapper.toResponseDto(user)

        assertEquals(1, responseDto.id)
        assertEquals("John", responseDto.name)
        assertEquals("Doe", responseDto.surname)
        assertEquals("john.doe@example.com", responseDto.email)
    }

    @Test
    fun `should update User fields from UserUpdateDto`() {
        val user = User.createDefault(id = 1)
        val updateDto = UserUpdateDto(
            name = "UpdatedName",
            surname = "UpdatedSurname",
            email = "updated.email@example.com"
        )

        userMapper.update(user, updateDto)

        assertEquals("UpdatedName", user.name)
        assertEquals("UpdatedSurname", user.surname)
        assertEquals("updated.email@example.com", user.email)
        assertEquals("hashed_password", user.password)
    }

    @Test
    fun `should map User to UserCredentialsDto`() {
        val role1 = Role(name = RoleName.USER)
        val role2 = Role(name = RoleName.ADMIN)
        val user = User.createDefault().apply {
            roles.addAll(setOf(role1, role2))
        }

        val credentialsDto: UserCredentialsDto = userMapper.toCredentialsDto(user)

        assertEquals(user.email, credentialsDto.email)
        assertEquals(user.password, credentialsDto.password)
        assertArrayEquals(arrayOf("USER", "ADMIN"), credentialsDto.roles)
    }
}
