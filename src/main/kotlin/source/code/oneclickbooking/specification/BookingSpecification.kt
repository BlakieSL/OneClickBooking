package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.BookingFilterKey
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.model.Booking
import java.time.LocalDate

class BookingSpecification(private val criteria: FilterCriteria): Specification<Booking> {
    override fun toPredicate(
        root: Root<Booking>,
        query: CriteriaQuery<*>?,
        builder: CriteriaBuilder
    ): Predicate {
        return when (criteria.filterKey) {
            BookingFilterKey.EMPLOYEE.name ->
                handleEntityProperty(root, "employee", builder)

            BookingFilterKey.SERVICE_POINT.name ->
                handleEntityProperty(root, "servicePoint", builder)

            BookingFilterKey.DATE.name ->
                handleDateProperty(root.get("date"), builder)

            else -> throw IllegalStateException("Unexpected filter key: ${criteria.filterKey}")
        }
    }

    private fun handleEntityProperty(
        root: Root<Booking>,
        joinProperty: String,
        builder: CriteriaBuilder
    ): Predicate {
        val join = root.join<Any, Any>(joinProperty)
        val value = criteria.value
        return when (criteria.operation) {
            FilterOperation.EQUAL -> builder.equal(join.get<Any>("id"), value)
            FilterOperation.NOT_EQUAL -> builder.notEqual(join.get<Any>("id"), value)
            else -> throw IllegalArgumentException(
                "Unsupported operation for $joinProperty: ${criteria.operation}"
            )
        }
    }

    private fun handleDateProperty(path: Path<LocalDate>, builder: CriteriaBuilder): Predicate {
        val value = criteria.value as? LocalDate
            ?: throw IllegalArgumentException("Value must be a LocalDate for date filtering")
        return when (criteria.operation) {
            FilterOperation.GREATER_THAN -> builder.greaterThan(path, value)
            FilterOperation.EQUAL -> builder.equal(path, value)
            else -> throw IllegalArgumentException(
                "Unsupported operation for date: ${criteria.operation}"
            )
        }
    }
}