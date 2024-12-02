package source.code.oneclickbooking.service.implementation

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.UserMapper
import source.code.oneclickbooking.model.User
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.declaration.JsonPatchService
import source.code.oneclickbooking.service.declaration.UserService
import source.code.oneclickbooking.service.declaration.ValidationService

@Service
class UserServiceImpl(
    private val validationService: ValidationService,
    private val jsonPatchService: JsonPatchService,
    private val mapper: UserMapper,
    private val repository: UserRepository,
): UserService{
    override fun createUser(request: UserCreateDto): UserResponseDto {
        val user = mapper.toEntity(request)
        val savedUser = repository.save(user)

        return mapper.toResponseDto(savedUser)
    }

    override fun deleteUser(id: Int) {
        val user = find(id)
        repository.delete(user)
    }

    override fun updateUser(id: Int, patch: JsonMergePatch): UserResponseDto {
        val user = find(id)
        val patched = applyPatch(user, patch)

        validationService.validate(patched)
        mapper.update(user, patched)
        val savedUser = repository.save(user)

        return mapper.toResponseDto(savedUser)
    }

    override fun getUser(id: Int): UserResponseDto {
        val user = find(id)
        return mapper.toResponseDto(user)
    }

    private fun applyPatch(user: User, patch: JsonMergePatch) : UserUpdateDto {
        val userDto = mapper.toResponseDto(user);
        return jsonPatchService.applyPatch(patch, userDto, UserUpdateDto::class)
    }

    private fun find(id: Int) : User {
        return repository.findById(id).orElseThrow { RecordNotFoundException(User::class, id) }
    }
}