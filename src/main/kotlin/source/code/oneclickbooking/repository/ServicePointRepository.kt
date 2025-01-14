package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import source.code.oneclickbooking.model.ServicePoint

interface ServicePointRepository : JpaRepository<ServicePoint, Int> {

    @EntityGraph(attributePaths = ["employeeAssociations.employee.treatments"])
    @Query("SELECT s FROM ServicePoint s WHERE s.id = :id")
    fun findByIdWithAssociations ( id: Int): ServicePoint?


}