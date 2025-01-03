package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockMultipartFile
import source.code.oneclickbooking.dto.request.ImageCreateDto
import source.code.oneclickbooking.dto.response.ImageResponseDto
import source.code.oneclickbooking.mapper.ImageMapper
import source.code.oneclickbooking.model.EntityType
import source.code.oneclickbooking.model.Image
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ImageMapperTest {
    @InjectMocks
    private lateinit var imageMapper: ImageMapper

    private lateinit var image: Image
    private lateinit var imageCreateDto: ImageCreateDto
    private lateinit var imageResponseDto: ImageResponseDto

    @Test
    fun `should map Image to ImageResponseDto`() {
        image = Image(
            id = 1,
            image = byteArrayOf(1, 2, 3),
            parentType = EntityType.SERVICE_POINT,
            parentId = 1
        )
        val result = imageMapper.toResponseDto(image)

        assertEquals(image.id, result.id)
        assertEquals(image.image, result.image)
        assertEquals(image.parentType, result.parentType)
        assertEquals(image.parentId, result.parentId)
    }

    @Test
    fun `should map ImageCreateDto to Image`() {
        val mockMultipartFile = MockMultipartFile(
            "file",
            "test.jpg",
            "image/jpeg",
            byteArrayOf(4, 5, 6)
        )

        val imageCreateDto = ImageCreateDto(
            image = mockMultipartFile,
            parentType = EntityType.REVIEW,
            parentId = 2
        )

        val result = imageMapper.toEntity(imageCreateDto)

        assertEquals(imageCreateDto.image.bytes, result.image)
        assertEquals(imageCreateDto.parentType, result.parentType)
        assertEquals(imageCreateDto.parentId, result.parentId)
    }
}