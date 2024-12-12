package source.code.oneclickbooking.service.declaration

import source.code.oneclickbooking.dto.response.TreatmentResponseDto
import source.code.oneclickbooking.model.Treatment

interface TreatmentService {
    fun get(id: Int): TreatmentResponseDto
    fun getAll(): List<TreatmentResponseDto>
    fun getAll(servicePointId: Int): List<TreatmentResponseDto>
}