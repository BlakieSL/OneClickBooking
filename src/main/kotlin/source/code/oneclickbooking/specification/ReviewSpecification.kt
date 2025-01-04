package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.dto.other.ReviewFilterKey
import source.code.oneclickbooking.exception.InvalidFilterKeyException
import source.code.oneclickbooking.exception.InvalidFilterOperationException
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.model.Review

class ReviewSpecification(private val criteria: FilterCriteria) : Specification<Review> {
    override fun toPredicate(
        root: Root<Review>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        return when (criteria.filterKey) {
            ReviewFilterKey.EMPLOYEE.name -> GenericSpecificationHelper.buildPredicateJoinProperty(
                builder = criteriaBuilder,
                criteria = criteria,
                root = root,
                joinProperty = "booking",
                subJoinProperty = "employee"
            )
            ReviewFilterKey.SERVICE_POINT.name -> GenericSpecificationHelper.buildPredicateJoinProperty(
                builder = criteriaBuilder,
                criteria = criteria,
                root = root,
                joinProperty = "booking",
                subJoinProperty = "servicePoint"
            )
            ReviewFilterKey.TEXT.name -> GenericSpecificationHelper.buildPredicateTextProperty(
                builder = criteriaBuilder,
                criteria = criteria,
                root = root
            )
            ReviewFilterKey.USER.name -> GenericSpecificationHelper.buildPredicateJoinProperty(
                builder = criteriaBuilder,
                criteria = criteria,
                root = root,
                joinProperty = "booking",
                subJoinProperty = "user"
            )
            else -> throw InvalidFilterKeyException(criteria.filterKey)
        }
    }
}
