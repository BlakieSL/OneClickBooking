package source.code.oneclickbooking.repository


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import source.code.oneclickbooking.model.Treatment

interface TreatmentRepository : JpaRepository<Treatment, Int> {
    @Query(""" 
        SELECT t
        FROM Treatment t
        JOIN t.employees e 
        JOIN e.servicePointAssociations spa ON spa.servicePoint.id = :servicePointId
    """)
    fun findAllByServicePointId (servicePointId: Int): List<Treatment>
}