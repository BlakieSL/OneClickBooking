package source.code.oneclickbooking.service.implementation

import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.service.declaration.JsonPatchService
import source.code.oneclickbooking.service.declaration.UserService
import source.code.oneclickbooking.service.declaration.ValidationService

@Service
class UserServiceImpl (
    private val validationService: ValidationService,
    private val jsonPatchService: JsonPatchService,
): UserService{
    override fun createUser(request: UserCreateDto): UserResponseDto {
        TODO("Not yet implemented")
    }

    override fun deleteUser(id: Int): UserResponseDto {
        TODO("Not yet implemented")
    }

    override fun updateUser(id: Int, request: UserUpdateDto): UserResponseDto {
        TODO("Not yet implemented")
    }

    override fun getUser(id: Int): UserResponseDto {
        TODO("Not yet implemented")
    }

    override fun getUserId(email: String): Int {
        TODO("Not yet implemented")
    }

}