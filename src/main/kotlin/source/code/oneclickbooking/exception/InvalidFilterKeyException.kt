package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.MessageResolver

class InvalidFilterKeyException(filterKey: String) : RuntimeException(
    MessageResolver.getMessage(
        key = ExceptionMessages.INVALID_FILTER_OPERATION,
        args = arrayOf(filterKey)
    )
)