package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.Role
import source.code.oneclickbooking.model.RoleName

interface RoleRepository : JpaRepository<Role, Int> {
    fun findByName(name: RoleName): Role?
}