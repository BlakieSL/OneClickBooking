package source.code.oneclickbooking.mapper

import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import source.code.oneclickbooking.dto.request.ImageCreateDto
import source.code.oneclickbooking.dto.response.ImageResponseDto
import source.code.oneclickbooking.exception.FileProcessingException
import source.code.oneclickbooking.model.Image
import java.io.IOException

@Component
class ImageMapper {
    fun toResponseDto(image: Image): ImageResponseDto {
        return ImageResponseDto(
            id = image.id!!,
            image = image.image,
            parentType = image.parentType,
            parentId = image.parentId,
        )
    }

    fun toEntity(dto: ImageCreateDto): Image {
        return Image(
            image = multipartFileToByteArray(dto.image),
            parentType = dto.parentType,
            parentId = dto.parentId,
        )
    }

    private fun multipartFileToByteArray(file: MultipartFile): ByteArray {
        return try {
            file.bytes
        } catch (e: IOException) {
            throw FileProcessingException(cause = e)
        }
    }
}