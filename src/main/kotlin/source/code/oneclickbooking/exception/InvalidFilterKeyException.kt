package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.MessageResolver

class InvalidFilterKeyException(filterKey: String) : LocalizedException(
    messageKey = ExceptionMessages.INVALID_FILTER_KEY,
    args = arrayOf(filterKey)
)