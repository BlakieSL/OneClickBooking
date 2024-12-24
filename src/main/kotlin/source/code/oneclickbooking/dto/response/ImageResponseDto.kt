package source.code.oneclickbooking.dto.response

import source.code.oneclickbooking.model.EntityType

data class ImageResponseDto(
    val id: Int,
    val image: ByteArray,
    val parentType: EntityType,
    val parentId: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageResponseDto

        if (id != other.id) return false
        if (parentId != other.parentId) return false
        if (!image.contentEquals(other.image)) return false
        if (parentType != other.parentType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + parentId
        result = 31 * result + image.contentHashCode()
        result = 31 * result + parentType.hashCode()
        return result
    }
}
