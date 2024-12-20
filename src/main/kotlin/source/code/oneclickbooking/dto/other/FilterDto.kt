package source.code.oneclickbooking.dto.other

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class FilterDto(
    @field:NotEmpty
    var filterCriteria: List<FilterCriteria> = emptyList(),
    var dataOption: FilterDataOption? = null
)

enum class FilterDataOption {
    AND, OR
}
