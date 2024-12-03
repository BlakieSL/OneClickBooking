package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.User

interface UserRepository : JpaRepository<User, Int> {
    @EntityGraph(attributePaths = ["roles"])
    fun findUserByEmail(email: String): User ?
}