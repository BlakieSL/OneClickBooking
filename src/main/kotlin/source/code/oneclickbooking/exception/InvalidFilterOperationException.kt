package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.ExceptionMessages

class InvalidFilterOperationException(operation: String) : LocalizedException(
    messageKey = ExceptionMessages.INVALID_FILTER_OPERATION,
    args = arrayOf(operation)
)