package source.code.oneclickbooking.exception

import source.code.oneclickbooking.utils.ExceptionMessages

class InvalidFilterOperationException(operation: String) : LocalizedException(
    messageKey = ExceptionMessages.INVALID_FILTER_OPERATION,
    args = arrayOf(operation)
)