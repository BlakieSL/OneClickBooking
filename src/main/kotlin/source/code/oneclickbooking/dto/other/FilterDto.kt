package source.code.oneclickbooking.dto.other

data class FilterDto(
    var filterCriteria: List<FilterCriteria> = emptyList(),
    var dataOption: FilterDataOption? = null,
    var sortOption: SortOption? = null
)

enum class FilterDataOption {
    AND, OR
}

enum class SortOption {
    DATE_ASC,
    DATE_DESC,
    DATE_CLOSEST
}
