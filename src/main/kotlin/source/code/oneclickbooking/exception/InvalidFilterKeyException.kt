package source.code.oneclickbooking.exception

import source.code.oneclickbooking.utils.ExceptionMessages

class InvalidFilterKeyException(filterKey: String) : LocalizedException(
    messageKey = ExceptionMessages.INVALID_FILTER_KEY,
    args = arrayOf(filterKey)
)