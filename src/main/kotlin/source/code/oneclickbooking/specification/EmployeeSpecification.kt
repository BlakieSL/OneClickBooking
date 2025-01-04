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
    ): Predicate {
        return when (criteria.filterKey) {
            EmployeeFilterKey.SERVICE_POINT.name -> GenericSpecificationHelper.buildPredicateJoinProperty(
                builder = criteriaBuilder,
                criteria = criteria,
                root = root,
                joinProperty = "servicePointAssociations",
                subJoinProperty = "servicePoint"
            )
            EmployeeFilterKey.TREATMENT.name -> GenericSpecificationHelper.buildPredicateEntityProperty(
                builder = criteriaBuilder,
                criteria = criteria,
                root = root,
                joinProperty = "treatments"
            )
            else -> throw InvalidFilterOperationException(criteria.filterKey)
        }
    }
}