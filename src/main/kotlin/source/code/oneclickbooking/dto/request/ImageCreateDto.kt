package source.code.oneclickbooking.dto.request

import org.springframework.web.multipart.MultipartFile
import source.code.oneclickbooking.model.EntityType

data class ImageCreateDto (
    val image: MultipartFile,
    val parentType: EntityType,
    val parentId: Int,
)