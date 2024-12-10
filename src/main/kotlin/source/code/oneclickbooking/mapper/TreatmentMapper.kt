package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import source.code.oneclickbooking.dto.response.TreatmentResponseDto
import source.code.oneclickbooking.model.Treatment

@Component
class TreatmentMapper {
    fun toResponseDto(treatment: Treatment): TreatmentResponseDto {
        return TreatmentResponseDto(
            id = treatment.id!!,
            name = treatment.name,
            description = treatment.description,
            price = treatment.price,
            duration = treatment.duration
        )
    }
}