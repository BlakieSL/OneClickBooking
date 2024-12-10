package source.code.oneclickbooking.specification

import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.FilterCriteria

fun interface SpecificationFactory<T> {
    fun createSpecification(criteria: FilterCriteria): Specification<T>
}