package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.EntityType
import source.code.oneclickbooking.model.Image

interface ImageRepository : JpaRepository<Image, Int> {
    fun findAllByParentTypeAndParentId(parentType: EntityType, parentId: Int): List<Image>
    fun findFirstByParentTypeAndParentId(parentType: EntityType, parentId: Int): Image?
}