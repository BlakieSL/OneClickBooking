package source.code.oneclickbooking.service.implementation.image

import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.request.ImageCreateDto
import source.code.oneclickbooking.dto.response.ImageResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.ImageMapper
import source.code.oneclickbooking.model.EntityType
import source.code.oneclickbooking.model.Image
import source.code.oneclickbooking.repository.ImageRepository
import source.code.oneclickbooking.service.declaration.image.ImageService

@Service
class ImageServiceImpl(
    private val mapper: ImageMapper,
    private val repository: ImageRepository
) : ImageService {
    override fun create(dto: ImageCreateDto): ImageResponseDto {
        val image = mapper.toEntity(dto)
        val savedImage = repository.save(image)

        return mapper.toResponseDto(savedImage)
    }

    override fun delete(id: Int) {
        val image = find(id)
        repository.delete(image)
    }

    override fun get(id: Int): ImageResponseDto {
        val image = find(id)
        return mapper.toResponseDto(image)
    }

    override fun getAllImagesForParent(
        parentType: EntityType,
        parentId: Int
    ): List<ImageResponseDto> {
        return repository.findAllByParentTypeAndParentId(parentType, parentId)
            .map { mapper.toResponseDto(it) }
    }

    override fun getFirstImageForParent(
        parentType: EntityType,
        parentId: Int
    ): ImageResponseDto {
        val image = repository.findFirstByParentTypeAndParentId(parentType, parentId)
            ?: throw RecordNotFoundException(
                Image::class,
                "parentType" to parentType,
                "parentId" to parentId
            )
        return mapper.toResponseDto(image)
    }

    fun find(id: Int): Image {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException(Image::class, id)
        }
    }
}