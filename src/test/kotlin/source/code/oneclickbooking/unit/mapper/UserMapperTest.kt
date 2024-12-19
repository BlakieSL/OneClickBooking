package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import source.code.oneclickbooking.dto.other.UserCredentialsDto
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.mapper.UserMapper
import source.code.oneclickbooking.model.Role
import source.code.oneclickbooking.model.RoleName
import source.code.oneclickbooking.model.User

@ExtendWith(MockitoExtension::class)
class UserMapperTest {
    @InjectMocks
    private lateinit var userMapper: UserMapper

    private lateinit var user: User
    private lateinit var createDto: UserCreateDto

    @BeforeEach
    fun setUp() {
        createDto = UserCreateDto.createDefault()
        user = User.createDefault(id = 1)
    }

    @Test
    fun `should map UserCreateDto to User with hashed password`() {
        val hashedPassword = "HashedPassword1!"

        val user = userMapper.toEntity(createDto, hashedPassword)

        assertEquals(createDto.name, user.name)
        assertEquals(createDto.surname, user.surname)
        assertEquals(createDto.email, user.email)
        assertEquals(hashedPassword, user.password)
    }

    @Test
    fun `should map User to UserResponseDto`() {
        val user = User.createDefault(id = 1)

        val responseDto: UserResponseDto = userMapper.toResponseDto(user)

        assertEquals(user.id, responseDto.id)
        assertEquals(user.name, responseDto.name)
        assertEquals(user.surname, responseDto.surname)
        assertEquals(user.email, responseDto.email)
    }

    @Test
    fun `should update User fields from UserUpdateDto`() {
        val updateDto = UserUpdateDto(
            name = "UpdatedName",
            surname = "UpdatedSurname",
            email = "updated.email@example.com"
        )

        userMapper.update(user, updateDto)

        assertEquals(updateDto.name, user.name)
        assertEquals(updateDto.surname, user.surname)
        assertEquals(updateDto.email, user.email)
        assertEquals("hashed_password", user.password)
    }

    @Test
    fun `should map User to UserCredentialsDto`() {
        val role1 = Role(name = RoleName.USER)
        val role2 = Role(name = RoleName.ADMIN)
        val user = User.createDefault(roles = mutableSetOf(role1, role2))

        val credentialsDto: UserCredentialsDto = userMapper.toCredentialsDto(user)

        assertEquals(user.email, credentialsDto.email)
        assertEquals(user.password, credentialsDto.password)
        assertArrayEquals(arrayOf(role1.name.name, role2.name.name), credentialsDto.roles)
    }
}
