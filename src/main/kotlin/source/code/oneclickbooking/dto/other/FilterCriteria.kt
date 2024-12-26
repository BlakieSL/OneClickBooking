package source.code.oneclickbooking.dto.other

data class FilterCriteria(
    var filterKey: String,
    var value: Any,
    var operation: FilterOperation
)

enum class FilterOperation {
    CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL, BEGINS_WITH,
    DOES_NOT_BEGIN_WITH, ENDS_WITH, DOES_NOT_END_WITH,
    NULL, NOT_NULL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN,
    LESS_THAN_EQUAL
}

enum class BookingFilterKey {
    EMPLOYEE, SERVICE_POINT, DATE,
}

enum class ReviewFilterKey {
    EMPLOYEE, SERVICE_POINT, TEXT, DATE
}

enum class EmployeeFilterKey {
    SERVICE_POINT, TREATMENT
}