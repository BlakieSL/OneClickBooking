package source.code.oneclickbooking.dto.other

import jakarta.validation.constraints.NotNull

data class FilterDto(
    @field:NotNull
    var filterCriteria: List<FilterCriteria> = emptyList(),
    var dataOption: FilterDataOption? = null
)

enum class FilterDataOption {
    AND, OR
}
