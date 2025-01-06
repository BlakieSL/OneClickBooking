package source.code.oneclickbooking.specification

import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.FilterDataOption
import source.code.oneclickbooking.dto.other.FilterDto


class SpecificationBuilder<T>(
    private val filterDto: FilterDto,
    private val specificationFactory: SpecificationFactory<T>
) {
    fun build(): Specification<T> {
        val criteriaList = filterDto.filterCriteria
        if (criteriaList.isEmpty()) {
            return Specification.where(null)
        }

        var result = specificationFactory.createSpecification(criteriaList[0])

        for (i in 1 until criteriaList.size) {
            val spec = specificationFactory.createSpecification(criteriaList[i])

            result = if (filterDto.dataOption == FilterDataOption.AND) {
                Specification.where(result).and(spec)
            } else {
                Specification.where(result).or(spec)
            }
        }
        return result
    }
}