package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.JpaRepository
import source.code.oneclickbooking.model.Treatment

interface TreatmentRepository : JpaRepository<Treatment, Int> {
    fun findA
}