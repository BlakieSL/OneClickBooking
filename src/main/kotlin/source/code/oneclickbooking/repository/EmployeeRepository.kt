package source.code.oneclickbooking.repository

import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import source.code.oneclickbooking.model.Employee

interface EmployeeRepository : JpaRepository<Employee, Int>, JpaSpecificationExecutor<Employee> {
    @EntityGraph(attributePaths = ["availabilities"])
    @Query("""
        SELECT e
        FROM Employee e
        JOIN e.servicePointAssociations spa
        JOIN e.treatments t
        WHERE spa.servicePoint.id = :servicePointId
        AND t.id = :treatmentId
        """)
    fun findAllByServicePointIdAndTreatmentId(
        @Param("servicePointId") servicePointId: Int,
        @Param("treatmentId") treatmentId: Int): List<Employee>

    @EntityGraph(attributePaths = ["availabilities"])
    @Query("""
        SELECT e
        FROM Employee e
        WHERE e.id = :id
        """)
    fun findByIdWithAvailabilities(@Param("id") id: Int): Employee?

    @EntityGraph(attributePaths = [
        "servicePointAssociations",
        "treatments",
        "availabilities",
    ])
    @Query("""
        SELECT e
        FROM Employee e
        WHERE e.id = :id
        """)
    fun findByIdWithAssociations(@Param("id") id: Int): Employee?
}