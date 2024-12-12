package source.code.oneclickbooking.repository


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import source.code.oneclickbooking.model.Treatment

interface TreatmentRepository : JpaRepository<Treatment, Int> {
    @Query(""" 
        SELECT t
        FROM Treatment t
        JOIN t.employees e 
        JOIN e.servicePointAssociations spa
        WHERE spa.servicePoint.id = :servicePointId
    """)
    fun findAllByServicePointId(@Param("servicePointId") servicePointId: Int): List<Treatment>
}