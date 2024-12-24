package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.MessageResolver

class InvalidRefreshTokenException(messageKey: String, vararg args: Any) : LocalizedException(
    messageKey = messageKey,
    args = args
)