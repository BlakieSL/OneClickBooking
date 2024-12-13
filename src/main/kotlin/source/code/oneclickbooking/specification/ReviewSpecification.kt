package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.BookingFilterKey
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.model.Review

class ReviewSpecification(private val criteria: FilterCriteria) : Specification<Review> {
    override fun toPredicate(
        root: Root<Review>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val bookingJoin = root.join<Any, Any>("booking")

        return when (criteria.filterKey) {
            BookingFilterKey.EMPLOYEE.name ->
                handleEntityProperty(bookingJoin, "employee", criteriaBuilder)

            BookingFilterKey.SERVICE_POINT.name ->
                handleEntityProperty(bookingJoin, "servicePoint", criteriaBuilder)

            else -> throw IllegalStateException("Unexpected filter key: ${criteria.filterKey}")
        }
    }

    private fun handleEntityProperty(
        bookingJoin: Join<Any, Any>,
        joinProperty: String,
        builder: CriteriaBuilder
    ): Predicate {
        val subJoin = bookingJoin.join<Any, Any>(joinProperty)
        val value = criteria.value
        return when (criteria.operation) {
            FilterOperation.EQUAL -> builder.equal(subJoin.get<Any>("id"), value)
            FilterOperation.NOT_EQUAL -> builder.notEqual(subJoin.get<Any>("id"), value)
            else -> throw IllegalArgumentException(
                "Unsupported operation for $joinProperty: ${criteria.operation}"
            )
        }
    }
}
