package source.code.oneclickbooking.service.declaration

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto

interface UserService {
    fun createUser(request: UserCreateDto) : UserResponseDto
    fun deleteUser(id: Int)
    fun updateUser(id: Int, patch: JsonMergePatch) : UserResponseDto
    fun getUser(id: Int) : UserResponseDto
}