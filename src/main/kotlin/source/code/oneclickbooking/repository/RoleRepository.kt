package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.Role

interface RoleRepository : JpaRepository<Role, Int> {
}