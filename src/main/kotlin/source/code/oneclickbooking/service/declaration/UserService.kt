package source.code.oneclickbooking.service.declaration

import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto

interface UserService {
    fun createUser(request: UserCreateDto) : UserResponseDto;
    fun deleteUser(id: Int) : UserResponseDto;
    fun updateUser(id: Int, request: UserUpdateDto) : UserResponseDto;
    fun getUser(id: Int) : UserResponseDto;
    fun getUserId(email: String) : Int;
}