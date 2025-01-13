package source.code.oneclickbooking.specification

import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.BookingFilterKey
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.exception.InvalidFilterKeyException
import source.code.oneclickbooking.model.Booking
import java.time.LocalDateTime

class BookingSpecification(private val criteria: FilterCriteria): Specification<Booking> {
    override fun toPredicate(
        root: Root<Booking>,
        query: CriteriaQuery<*>?,
        builder: CriteriaBuilder
    ): Predicate {
        query?.orderBy(builder.desc(root.get<LocalDateTime>("date")))

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