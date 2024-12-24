package source.code.oneclickbooking.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile
import source.code.oneclickbooking.model.EntityType

data class ImageCreateDto (
    @JsonProperty(required = true)
    @field:NotNull
    val image: MultipartFile,

    @JsonProperty(required = true)
    @field:NotNull
    val parentType: EntityType,

    @JsonProperty(required = true)
    @field:NotNull
    val parentId: Int,
)