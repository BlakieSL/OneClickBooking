package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import source.code.oneclickbooking.model.User

interface UserRepository : JpaRepository<User, Int> {
    @EntityGraph(attributePaths = ["roles"])
    fun findUserByEmail(email: String): User ?

    fun existsUserByEmail(email: String) : Boolean
}