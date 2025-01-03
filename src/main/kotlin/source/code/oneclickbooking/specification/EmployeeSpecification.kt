package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.EmployeeFilterKey
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.exception.InvalidFilterOperationException
import source.code.oneclickbooking.model.Employee

class EmployeeSpecification(private val criteria: FilterCriteria): Specification<Employee> {
    override fun toPredicate(
        root: Root<Employee>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        return when (criteria.filterKey) {
            EmployeeFilterKey.SERVICE_POINT.name -> handleJoinPredicate(
                root = root,
                joinProperty = "servicePointAssociations",
                subJoinProperty = "servicePoint",
                criteriaBuilder = criteriaBuilder
            )
            EmployeeFilterKey.TREATMENT.name -> handleJoinPredicate(
                root = root,
                joinProperty = "treatments",
                subJoinProperty = null,
                criteriaBuilder = criteriaBuilder
            )
            else -> throw InvalidFilterOperationException(criteria.filterKey)
        }
    }

    private fun handleJoinPredicate(
        root: Root<Employee>,
        joinProperty: String,
        subJoinProperty: String?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val join = if (subJoinProperty != null) {
            val servicePointAssociationJoin = root.join<Any, Any>(joinProperty)
            servicePointAssociationJoin.join<Any, Any>(subJoinProperty)
        } else {
            root.join<Employee, Any>(joinProperty)
        }

        val value = criteria.value
        return when (criteria.operation) {
            FilterOperation.EQUAL -> criteriaBuilder.equal(join.get<Any>("id"), value)
            FilterOperation.NOT_EQUAL -> criteriaBuilder.notEqual(join.get<Any>("id"), value)
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }
}