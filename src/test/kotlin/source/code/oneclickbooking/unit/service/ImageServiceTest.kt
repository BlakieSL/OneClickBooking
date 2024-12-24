package source.code.oneclickbooking.unit.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.mock.web.MockMultipartFile
import source.code.oneclickbooking.dto.request.ImageCreateDto
import source.code.oneclickbooking.dto.response.ImageResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.ImageMapper
import source.code.oneclickbooking.model.EntityType
import source.code.oneclickbooking.model.Image
import source.code.oneclickbooking.repository.ImageRepository
import source.code.oneclickbooking.service.declaration.image.ImageService
import source.code.oneclickbooking.service.implementation.image.ImageServiceImpl

@ExtendWith(MockitoExtension::class)
class ImageServiceTest {
    @InjectMocks
    private lateinit var imageService: ImageServiceImpl

    @Mock
    private lateinit var imageMapper: ImageMapper

    @Mock
    private lateinit var imageRepository: ImageRepository

    private lateinit var image: Image
    private lateinit var imageResponseDto: ImageResponseDto
    private lateinit var imageCreateDto: ImageCreateDto

    @BeforeEach
    fun setup() {
        val mockMultipartFile = MockMultipartFile(
            "file",
            "test.jpg",
            "image/jpeg",
            byteArrayOf(1, 2, 3)
        )

        image = Image(
            id = 1,
            image = byteArrayOf(1, 2, 3),
            parentType = EntityType.SERVICE_POINT,
            parentId = 1
        )

        imageResponseDto = ImageResponseDto(
            id = 1,
            image = byteArrayOf(1, 2, 3),
            parentType = EntityType.SERVICE_POINT,
            parentId = 1
        )

        imageCreateDto = ImageCreateDto(
            image = mockMultipartFile,
            parentType = EntityType.SERVICE_POINT,
            parentId = 1
        )

    }

    @Test
    fun `should create image`() {
        whenever(imageMapper.toEntity(imageCreateDto)).thenReturn(image)
        whenever(imageRepository.save(image)).thenReturn(image)
        whenever(imageMapper.toResponseDto(image)).thenReturn(imageResponseDto)

        val result = imageService.create(imageCreateDto)

        assertEquals(imageResponseDto, result)
        verify(imageRepository).save(image)
        verify(imageMapper).toResponseDto(image)
    }

    @Test
    fun `should delete image`() {
        whenever(imageRepository.findById(1)).thenReturn(java.util.Optional.of(image))

        imageService.delete(1)

        verify(imageRepository).delete(image)
    }

    @Test
    fun `test delete should throw exception when image not found`() {
        whenever(imageRepository.findById(1)).thenReturn(java.util.Optional.empty())

        assertThrows<RecordNotFoundException> {
            imageService.delete(1)
        }
    }

    @Test
    fun `should get image`() {
        whenever(imageRepository.findById(1)).thenReturn(java.util.Optional.of(image))
        whenever(imageMapper.toResponseDto(image)).thenReturn(imageResponseDto)

        val result = imageService.get(1)

        assertEquals(imageResponseDto, result)
        verify(imageMapper).toResponseDto(image)
    }

    @Test
    fun `test get should throw exception when image not found`() {
        whenever(imageRepository.findById(1)).thenReturn(java.util.Optional.empty())

        assertThrows<RecordNotFoundException> {
            imageService.get(1)
        }
    }

    @Test
    fun `should get all images for parent`() {
        val images = listOf(image)
        val imageResponseDtos = listOf(imageResponseDto)

        whenever(imageRepository.findAllByParentTypeAndParentId(EntityType.SERVICE_POINT, 1))
            .thenReturn(images)
        whenever(imageMapper.toResponseDto(image)).thenReturn(imageResponseDto)

        val result = imageService.getAllImagesForParent(EntityType.SERVICE_POINT, 1)

        assertEquals(imageResponseDtos, result)
        verify(imageRepository).findAllByParentTypeAndParentId(EntityType.SERVICE_POINT, 1)
    }
}