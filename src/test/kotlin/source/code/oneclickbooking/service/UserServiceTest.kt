package source.code.oneclickbooking.service

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.*
import org.springframework.security.crypto.password.PasswordEncoder
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.UserMapper
import source.code.oneclickbooking.model.User
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.declaration.JsonPatchService
import source.code.oneclickbooking.service.declaration.ValidationService
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
        userCreateDto = UserCreateDto.createDefault()
        userUpdateDto = UserUpdateDto(name = "UpdatedName")
        user = User.createDefault()
        hashedPassword = "hashed_password"
        userResponseDto = UserResponseDto(1, user.name, user.surname, user.email)
        patch = mock<JsonMergePatch>()
    }

    @Test
    fun `should create user`() {
        val savedUser = user.copy(id = 1)

        whenever(userMapper.toEntity(userCreateDto, hashedPassword)).thenReturn(user)
        whenever(userRepository.save(user)).thenReturn(savedUser)
        whenever(userMapper.toResponseDto(savedUser)).thenReturn(userResponseDto)
        whenever(passwordEncoder.encode(userCreateDto.password)).thenReturn(hashedPassword)

        val result = userService.createUser(userCreateDto)

        assertEquals(userResponseDto, result)
        verify(userMapper).toEntity(userCreateDto, hashedPassword)
        verify(userRepository).save(user)
        verify(userMapper).toResponseDto(savedUser)
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
        val updatedUser = user.copy(name = "UpdatedName")
        val responseDtoBeforePatch = UserResponseDto(1, user.name, user.surname, user.email)
        val responseDtoAfterUpdate = UserResponseDto(
            1,
            "UpdatedName",
            user.surname,
            user.email
        )
        val userCaptor = argumentCaptor<User>()

        whenever(userRepository.findById(1)).thenReturn(Optional.of(user))
        whenever(userMapper.toResponseDto(user)).thenReturn(responseDtoBeforePatch)
        whenever(jsonPatchService.applyPatch(patch, responseDtoBeforePatch, UserUpdateDto::class))
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
        verify(jsonPatchService).applyPatch(patch, responseDtoBeforePatch, UserUpdateDto::class)
        verify(validationService).validate(userUpdateDto)
        verify(userMapper).update(user, userUpdateDto)
        verify(userRepository).save(user)
        verify(userMapper, times(2)).toResponseDto(userCaptor.capture())
        assertEquals(user, userCaptor.firstValue)
        assertEquals(updatedUser, userCaptor.secondValue)
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
        whenever(userMapper.toResponseDto(user)).thenReturn(responseDtoBeforePatch)
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
        whenever(userMapper.toResponseDto(user)).thenReturn(responseDtoBeforePatch)
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
}