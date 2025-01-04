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
            BookingFilterKey.EMPLOYEE.name -> GenericSpecificationHelper.buildPredicateEntityProperty(
                builder = builder,
                criteria = criteria,
                root = root,
                joinProperty = "employee"
            )
            BookingFilterKey.SERVICE_POINT.name -> GenericSpecificationHelper.buildPredicateEntityProperty(
                builder = builder,
                criteria = criteria,
                root = root,
                joinProperty = "servicePoint"
            )
            BookingFilterKey.DATE.name -> GenericSpecificationHelper.buildPredicateDateProperty(
                builder = builder,
                criteria = criteria,
                root = root
            )
            BookingFilterKey.USER.name -> GenericSpecificationHelper.buildPredicateEntityProperty(
                builder = builder,
                criteria = criteria,
                root = root,
                joinProperty = "user"
            )
            else -> throw InvalidFilterKeyException(criteria.filterKey)
        }
    }
}