package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterOperation
import source.code.oneclickbooking.exception.InvalidFilterOperationException
import source.code.oneclickbooking.exception.LocalizedIllegalArgument
import source.code.oneclickbooking.utils.ExceptionMessages
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeParseException

object GenericSpecificationHelper {

    fun <T> buildPredicateEntityProperty(
        builder: CriteriaBuilder,
        criteria: FilterCriteria,
        root: Root<T>,
        joinProperty: String
    ): Predicate {
        val join = root.join<Any, Any>(joinProperty)
        val value = criteria.value;
        return when (criteria.operation) {
            FilterOperation.EQUAL -> builder.equal(join.get<Any>("id"), value)
            FilterOperation.NOT_EQUAL -> builder.notEqual(join.get<Any>("id"), value)
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

    fun <T> buildPredicateJoinProperty(
        builder: CriteriaBuilder,
        criteria: FilterCriteria,
        root: Root<T>,
        joinProperty: String,
        subJoinProperty: String
    ): Predicate {
        val join = root.join<Any, Any>(joinProperty);
        val subJoin = join.join<Any, Any>(subJoinProperty);
        val value = criteria.value;

        return when (criteria.operation) {
            FilterOperation.EQUAL -> builder.equal(subJoin.get<Any>("id"), value)
            FilterOperation.NOT_EQUAL -> builder.notEqual(subJoin.get<Any>("id"), value)
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

    fun <T> buildPredicateDateProperty(
        builder: CriteriaBuilder,
        criteria: FilterCriteria,
        root: Root<T>
    ): Predicate {
        val value = parseDateValue(criteria.value);
        return when (criteria.operation) {
            FilterOperation.EQUAL ->
                builder.equal(
                    builder.function(
                        "DATE",
                        LocalDateTime::class.java,
                        root.get<LocalDateTime>("date")
                    ),
                    value
                )

            else -> throw LocalizedIllegalArgument(
                messageKey = ExceptionMessages.INVALID_FILTER_OPERATION,
                args = arrayOf(criteria.operation.name)
            )
        }
    }

    fun <T> buildPredicateTextProperty(
        builder: CriteriaBuilder,
        criteria: FilterCriteria,
        root: Root<T>
    ): Predicate {
        return when (criteria.value) {
            "NOT_NULL" -> builder.isNotNull(root.get<Any>("text"))
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

    fun <T> buildPredicateStatusProperty(
        builder: CriteriaBuilder,
        criteria: FilterCriteria,
        root: Root<T>
    ): Predicate {
        return when (criteria.operation) {
            FilterOperation.EQUAL -> builder.equal(root.get<Any>("status"), criteria.value)
            else -> throw InvalidFilterOperationException(criteria.operation.name)
        }
    }

    private fun parseDateValue(rawValue: Any): LocalDate {
        return try {
            LocalDate.parse(rawValue as String)
        } catch (e: DateTimeParseException) {
            throw LocalizedIllegalArgument(
                messageKey = ExceptionMessages.INVALID_DATE_FORMAT,
                args = arrayOf(rawValue)
            )
        }
    }
}
