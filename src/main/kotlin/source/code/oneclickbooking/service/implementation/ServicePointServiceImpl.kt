package source.code.oneclickbooking.service.implementation

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.response.ServicePointResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.ServicePointMapper
import source.code.oneclickbooking.model.ServicePoint
import source.code.oneclickbooking.repository.ServicePointRepository
import source.code.oneclickbooking.service.declaration.ServicePointService

@Service
class ServicePointServiceImpl(
    private val mapper: ServicePointMapper,
    private val repository: ServicePointRepository
) : ServicePointService{
    @Cacheable(value = ["servicePoints"], key = "#id")
    override fun get(id: Int): ServicePointResponseDto {
        return repository.findById(id)
            .map { mapper.toResponseDto(it) }
            .orElseThrow { RecordNotFoundException(ServicePoint::class, id) }
    }

    @Cacheable(value = ["allServicePoints"])
    override fun getAll(): List<ServicePointResponseDto> {
        return repository.findAll()
            .map { mapper.toResponseDto(it) }
    }
}