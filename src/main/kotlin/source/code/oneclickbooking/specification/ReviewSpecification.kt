package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.dto.other.ReviewFilterKey
import source.code.oneclickbooking.exception.InvalidFilterKeyException
import source.code.oneclickbooking.exception.InvalidFilterOperationException
import source.code.oneclickbooking.model.Review

class ReviewSpecification(private val criteria: FilterCriteria) : Specification<Review> {
    override fun toPredicate(
        root: Root<Review>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val bookingFetch = root.fetch<Any, Any>("booking").apply {
            fetch<Any, Any>("user")
            fetch<Any, Any>("employee")
        }

        return when (criteria.filterKey) {
            ReviewFilterKey.EMPLOYEE.name ->
                handleEntityProperty(bookingFetch as Join<Any, Any>, "employee", criteriaBuilder)

            ReviewFilterKey.SERVICE_POINT.name ->
                handleEntityProperty(bookingFetch as Join<Any, Any>, "servicePoint", criteriaBuilder)

            ReviewFilterKey.TEXT.name ->
                handleTextProperty(root, criteriaBuilder);

            ReviewFilterKey.USER.name -> handleJoinPredicate(
                root = root,
                joinProperty = "booking",
                subJoinProperty = "user",
                criteriaBuilder = criteriaBuilder
            )

            else -> throw InvalidFilterKeyException(criteria.filterKey)
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
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

    private fun handleTextProperty(
        root: Root<Review>,
        builder: CriteriaBuilder
    ): Predicate {
        return if (criteria.value == "NOT_NULL") {
            builder.isNotNull(root.get<Any>("text"))
        } else {
            throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

    private fun handleJoinPredicate(
        root: Root<Review>,
        joinProperty: String,
        subJoinProperty: String,
        criteriaBuilder: CriteriaBuilder
    ): Predicate {
        val join = root.join<Any, Any>(joinProperty)
        val subJoin = join.join<Any, Any>(subJoinProperty)
        val value = criteria.value
        return when (criteria.operation) {
            FilterOperation.EQUAL -> criteriaBuilder.equal(subJoin.get<Any>("id"), value)
            FilterOperation.NOT_EQUAL -> criteriaBuilder.notEqual(subJoin.get<Any>("id"), value)
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }
}
