package source.code.oneclickbooking.dto.other

data class FilterDto(
    var filterCriteria: List<FilterCriteria> = emptyList(),
    var dataOption: FilterDataOption? = null
)

enum class FilterDataOption {
    AND, OR
}
