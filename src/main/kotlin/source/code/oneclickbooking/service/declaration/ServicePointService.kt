package source.code.oneclickbooking.service.declaration

import source.code.oneclickbooking.dto.response.ServicePointResponseDto

interface ServicePointService {
    fun get(id: Int) : ServicePointResponseDto
    fun getAll() : List<ServicePointResponseDto>
}