package source.code.oneclickbooking.service.declaration.image

import source.code.oneclickbooking.dto.request.ImageCreateDto
import source.code.oneclickbooking.dto.response.ImageResponseDto
import source.code.oneclickbooking.model.EntityType

interface ImageService {
    fun create(dto: ImageCreateDto): ImageResponseDto
    fun delete(id: Int)
    fun get(id: Int): ImageResponseDto
    fun getAllImagesForParent(parentType: EntityType, parentId: Int): List<ImageResponseDto>
    fun getFirstImageForParent(parentType: EntityType, parentId: Int): ImageResponseDto?
}