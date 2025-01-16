package source.code.oneclickbooking.specification

import org.springframework.data.jpa.domain.Specification
import source.code.oneclickbooking.dto.other.FilterCriteria
import source.code.oneclickbooking.dto.other.FilterDataOption
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.other.SortOption
import java.time.LocalDateTime


class SpecificationBuilder<T>(
    private val filterDto: FilterDto,
    private val specificationFactory: SpecificationFactory<T>
) {
    fun build(): Specification<T> {
        val criteriaList = filterDto.filterCriteria

        val result = if (criteriaList.isEmpty()) {
            Specification.where(null)
        } else {
            buildFilterSpecification(criteriaList)
        }

        return applySorting(result)
    }

    private fun buildFilterSpecification(criteriaList: List<FilterCriteria>): Specification<T> {
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

    private fun applySorting(baseSpec: Specification<T>): Specification<T> {
        return Specification { root, query, builder ->
            val predicate = baseSpec.toPredicate(root, query, builder)

            filterDto.sortOption?.let { sortOption ->
                when (sortOption) {
                    SortOption.DATE_ASC -> query?.orderBy(builder.asc(root.get<LocalDateTime>("date")))
                    SortOption.DATE_DESC -> query?.orderBy(builder.desc(root.get<LocalDateTime>("date")))
                    SortOption.DATE_CLOSEST -> {
                        query?.orderBy(
                            builder.asc(
                                builder.function(
                                    "ABS",
                                    Int::class.java,
                                    builder.function(
                                        "custom_timestampdiff",
                                        Int::class.java,
                                        root.get<LocalDateTime>("date"),
                                        builder.currentTimestamp()
                                    )
                                )
                            )
                        )
                    }
                }
            }

            predicate
        }
    }
}