package source.code.oneclickbooking.service.implementation

import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.response.TreatmentResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.TreatmentMapper
import source.code.oneclickbooking.model.Treatment
import source.code.oneclickbooking.repository.TreatmentRepository
import source.code.oneclickbooking.service.declaration.TreatmentService

@Service
class TreatmentServiceImpl(
    private val mapper: TreatmentMapper,
    private val repository: TreatmentRepository,
): TreatmentService {
    override fun get(id: Int): TreatmentResponseDto {
        return find(id).let { mapper.toResponseDto(it) }
    }

    override fun getAll(): List<TreatmentResponseDto> {
        return repository.findAll().map { mapper.toResponseDto(it) }
    }

    override fun getAll(servicePointId: Int): List<TreatmentResponseDto> {
        TODO("Not yet implemented")
    }

    private fun find(id: Int): Treatment {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException(Treatment::class, id)
        }
    }
}