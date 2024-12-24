package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.MessageResolver

class InvalidFilterOperationException(operation: String) : RuntimeException(
    MessageResolver.getMessage(
        key = ExceptionMessages.INVALID_FILTER_KEY,
        args = arrayOf(operation)
    )
)