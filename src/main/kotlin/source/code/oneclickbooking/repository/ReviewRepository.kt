package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import source.code.oneclickbooking.model.Review

interface ReviewRepository : JpaRepository<Review, Int>, JpaSpecificationExecutor<Review> {
    fun findAllByTextNotNull(): List<Review>
}