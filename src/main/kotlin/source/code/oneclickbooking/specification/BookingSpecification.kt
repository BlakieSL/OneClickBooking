package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.*
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.BookingFilterKey
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.exception.InternalizedIllegalArgumentException
import source.code.oneclickbooking.exception.InvalidFilterKeyException
import source.code.oneclickbooking.exception.InvalidFilterOperationException
import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.model.Booking
import java.time.LocalDate
import java.time.format.DateTimeParseException

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

            else -> throw InvalidFilterKeyException(criteria.filterKey)
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
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

    private fun handleDateProperty(path: Path<LocalDate>, builder: CriteriaBuilder): Predicate {
        val value = try {
            when (criteria.value) {
                is LocalDate -> criteria.value as LocalDate
                is String -> LocalDate.parse(criteria.value as String)
                else -> throw InternalizedIllegalArgumentException(
                    messageKey = ExceptionMessages.UNSUPPORTED_DATE_VALUE_TYPE,
                    args = arrayOf(criteria.value.javaClass.name ?: "null")
                )
            }
        } catch (e: DateTimeParseException) {
            throw InternalizedIllegalArgumentException(
                messageKey = ExceptionMessages.INVALID_DATE_FORMAT,
                args = arrayOf(criteria.value)
            )
        }

        return when (criteria.operation) {
            FilterOperation.EQUAL ->
                builder.equal(builder.function("DATE", LocalDate::class.java, path), value)
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

}