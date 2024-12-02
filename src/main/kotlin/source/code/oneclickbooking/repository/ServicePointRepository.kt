package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.ServicePoint

interface ServicePointRepository : JpaRepository<ServicePoint, Int> {
}