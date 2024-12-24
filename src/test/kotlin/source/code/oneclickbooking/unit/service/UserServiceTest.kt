// UserServiceTest.kt
package source.code.oneclickbooking.unit.service

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.lenient
import org.mockito.kotlin.*
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import source.code.oneclickbooking.dto.other.UserCredentialsDto
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.exception.InternalizedIllegalArgumentException
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.MessageResolver
import source.code.oneclickbooking.mapper.UserMapper
import source.code.oneclickbooking.model.Role
import source.code.oneclickbooking.model.RoleName
import source.code.oneclickbooking.model.User
import source.code.oneclickbooking.repository.RoleRepository
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import source.code.oneclickbooking.service.declaration.util.ValidationService
import source.code.oneclickbooking.service.implementation.UserServiceImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceTest {
    @Mock
    private lateinit var passwordEncoder: PasswordEncoder
    @Mock
    private lateinit var validationService: ValidationService
    @Mock
    private lateinit var jsonPatchService: JsonPatchService
    @Mock
    private lateinit var userMapper: UserMapper
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var roleRepository: RoleRepository
    @InjectMocks
    private lateinit var userService: UserServiceImpl

    private lateinit var user: User
    private lateinit var userCreateDto: UserCreateDto
    private lateinit var userUpdateDto: UserUpdateDto
    private lateinit var userResponseDto: UserResponseDto
    private lateinit var hashedPassword: String
    private lateinit var patch: JsonMergePatch

    @BeforeEach
    fun setUp() {
        val mockMessageSource = mock<MessageSource>()
        lenient().`when`(mockMessageSource.getMessage(
            any(), any(), any())
        ).thenReturn("Just to Silence the Warnings")

        MessageResolver.setMessageSource(mockMessageSource)

        userCreateDto = UserCreateDto.createDefault()
        userUpdateDto = UserUpdateDto(name = "UpdatedName")
        user = User.createDefault()
        hashedPassword = "hashed_password"
        userResponseDto = UserResponseDto(1, user.name, user.surname, user.email)
        patch = mock<JsonMergePatch>()
    }

    @Test
    fun `should create user`() {
        val savedUser = User.createDefault(
            id = 1,
            name = user.name,
            surname = user.surname,
            email = user.email,
            password = user.password,
            roles = user.roles,
            bookings = user.bookings
        )
        val role = Role(name = RoleName.USER)

        whenever(userMapper.toEntity(userCreateDto, hashedPassword)).thenReturn(user)
        whenever(roleRepository.findByName(RoleName.USER)).thenReturn(role)
        whenever(userRepository.save(user)).thenReturn(savedUser)
        whenever(userMapper.toResponseDto(savedUser)).thenReturn(userResponseDto)
        whenever(passwordEncoder.encode(userCreateDto.password)).thenReturn(hashedPassword)

        val result = userService.createUser(userCreateDto)

        assertEquals(userResponseDto, result)
        verify(userMapper).toEntity(userCreateDto, hashedPassword)
        verify(roleRepository).findByName(RoleName.USER)
        verify(userRepository).save(user)
        verify(userMapper).toResponseDto(savedUser)
        assertEquals(1, user.roles.size)
        assertEquals(role, user.roles.first())
    }

    @Test
    fun `should throw exception when default role is not found`() {
        whenever(userMapper.toEntity(userCreateDto, hashedPassword)).thenReturn(user)
        whenever(passwordEncoder.encode(userCreateDto.password)).thenReturn(hashedPassword)
        whenever(roleRepository.findByName(RoleName.USER)).thenReturn(null)

        assertThrows<RecordNotFoundException> {
            userService.createUser(userCreateDto)
        }

        verify(userMapper).toEntity(userCreateDto, hashedPassword)
        verify(roleRepository).findByName(RoleName.USER)
        verifyNoInteractions(userRepository)
    }

    @Test
    fun `should delete user`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))

        userService.deleteUser(1)

        verify(userRepository).delete(user)
    }

    @Test
    fun `should throw exception when deleting non-existing user`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            userService.deleteUser(1)
        }
    }

    @Test
    fun `should update user`() {
        val updatedUser = User.createDefault(
            id = user.id,
            name = "UpdatedName",
            surname = user.surname,
            email = user.email,
            password = user.password,
            roles = user.roles,
            bookings = user.bookings
        )

        val responseDtoAfterUpdate = UserResponseDto(
            1,
            "UpdatedName",
            user.surname,
            user.email
        )
        val userCaptor = argumentCaptor<User>()

        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(jsonPatchService.applyPatch(patch, UserUpdateDto(), UserUpdateDto::class))
            .thenReturn(userUpdateDto)
        doNothing().whenever(validationService).validate(userUpdateDto)
        doAnswer {
            user.name = userUpdateDto.name!!
        }.whenever(userMapper).update(user, userUpdateDto)
        whenever(userRepository.save(user)).thenReturn(updatedUser)
        whenever(userMapper.toResponseDto(updatedUser)).thenReturn(responseDtoAfterUpdate)

        val result = userService.updateUser(1, patch)

        assertEquals(responseDtoAfterUpdate, result)
        verify(userRepository).findById(1)
        verify(jsonPatchService).applyPatch(patch, UserUpdateDto(), UserUpdateDto::class)
        verify(validationService).validate(userUpdateDto)
        verify(userMapper).update(user, userUpdateDto)
        verify(userRepository).save(user)
        verify(userMapper).toResponseDto(userCaptor.capture())

        assertEquals(user.id, userCaptor.firstValue.id)
        assertEquals("UpdatedName", userCaptor.firstValue.name)
    }

    @Test
    fun `should throw exception when updating non-existing user`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            userService.updateUser(1, patch)
        }
    }

    @Test
    fun `should throw exception when patch fails`() {
        val responseDtoBeforePatch = UserResponseDto(1, user.name, user.surname, user.email)

        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(jsonPatchService.applyPatch(patch, responseDtoBeforePatch, UserUpdateDto::class))
            .thenThrow(RuntimeException("Patch failed"))

        assertThrows<RuntimeException> {
            userService.updateUser(1, patch)
        }
    }

    @Test
    fun `should throw exception when validation fails`() {
        val responseDtoBeforePatch = UserResponseDto(1, user.name, user.surname, user.email)

        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(jsonPatchService.applyPatch(patch, responseDtoBeforePatch, UserUpdateDto::class))
            .thenReturn(userUpdateDto)
        doThrow(RuntimeException("Validation failed")).whenever(validationService)
            .validate(userUpdateDto)

        assertThrows<RuntimeException> {
            userService.updateUser(1, patch)
        }
    }

    @Test
    fun `should get user`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(userMapper.toResponseDto(user)).thenReturn(userResponseDto)

        val result = userService.getUser(1)

        assertEquals(userResponseDto, result)
        verify(userRepository).findById(1)
        verify(userMapper).toResponseDto(user)
    }

    @Test
    fun `should throw exception when getting non-existing user`() {
        whenever(userRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            userService.getUser(1)
        }
    }

    @Test
    fun `should load user by username successfully`() {
        val userCredentials = UserCredentialsDto(
            user.email,
            user.password,
            arrayOf("USER")
        )

        whenever(userRepository.findUserByEmail(user.email)).thenReturn(user)
        whenever(userMapper.toCredentialsDto(user)).thenReturn(userCredentials)

        val result = userService.loadUserByUsername(user.email)

        assertNotNull(result)
        assertEquals(user.email, result.username)
        assertEquals(user.password, result.password)
        assertArrayEquals(arrayOf("ROLE_USER"), result.authorities.map { it.authority }.toTypedArray())
        verify(userRepository).findUserByEmail(user.email)
    }

    @Test
    fun `should throw exception when user is not found`() {
        whenever(userRepository.findUserByEmail(user.email)).thenReturn(null)

        assertThrows<RecordNotFoundException> {
            userService.loadUserByUsername(user.email)
        }

        verify(userRepository).findUserByEmail(user.email)
    }

    @Test
    fun `should throw exception when username is null`() {
        assertThrows<InternalizedIllegalArgumentException> {
            userService.loadUserByUsername(null)
        }
    }

    @Test
    fun `should get user id successfully`() {
        val email = "test@example.com"
        val userId = 1
        val userWithId = User.createDefault(
            id = userId,
            name = user.name,
            surname = user.surname,
            email = user.email,
            password = user.password,
            roles = user.roles,
            bookings = user.bookings
        )
        whenever(userRepository.findUserByEmail(email)).thenReturn(userWithId)

        val result = userService.getUserId(email)

        assertEquals(userId, result)
        verify(userRepository).findUserByEmail(email)
    }

    @Test
    fun `should throw exception when user is not found by email`() {
        val email = "test@example.com"
        whenever(userRepository.findUserByEmail(email)).thenReturn(null)

        assertThrows<RecordNotFoundException> {
            userService.getUserId(email)
        }

        verify(userRepository).findUserByEmail(email)
    }
}
