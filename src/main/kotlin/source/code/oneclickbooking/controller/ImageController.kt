package source.code.oneclickbooking.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import source.code.oneclickbooking.annotation.ImageRelatedEntityAuthorOrAdmin
import source.code.oneclickbooking.annotation.ImageRelatedEntityAuthorOrAdminById
import source.code.oneclickbooking.dto.request.ImageCreateDto
import source.code.oneclickbooking.dto.response.ImageResponseDto
import source.code.oneclickbooking.model.EntityType
import source.code.oneclickbooking.service.declaration.image.ImageService

@RestController
@RequestMapping("/api/images")
class ImageController(
    private val imageService: ImageService
) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ResponseEntity<ImageResponseDto> =
        ResponseEntity.ok(imageService.get(id))

    @GetMapping("/parent/{parentType}/{parentId}")
    fun getAllImagesForParent(
        @PathVariable parentType: EntityType,
        @PathVariable parentId: Int
    ): ResponseEntity<List<ImageResponseDto>> =
        ResponseEntity.ok(imageService.getAllImagesForParent(parentType, parentId))

    @ImageRelatedEntityAuthorOrAdmin
    @PostMapping
    fun create(
        @Valid @ModelAttribute dto: ImageCreateDto
    ): ResponseEntity<ImageResponseDto> =
        ResponseEntity.ok(imageService.create(dto))

    @ImageRelatedEntityAuthorOrAdminById
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Unit> {
        imageService.delete(id)
        return ResponseEntity.noContent().build()
    }
}