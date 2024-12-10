package source.code.oneclickbooking.service.declaration

import source.code.oneclickbooking.dto.response.TreatmentResponseDto

interface TreatmentService {
    fun get(id: Int): TreatmentResponseDto
    fun getAll(): List<TreatmentResponseDto>
}