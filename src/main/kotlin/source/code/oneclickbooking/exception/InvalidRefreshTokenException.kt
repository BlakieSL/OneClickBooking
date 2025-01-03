package source.code.oneclickbooking.exception

class InvalidRefreshTokenException(messageKey: String, vararg args: Any) : LocalizedException(
    messageKey = messageKey,
    args = args
)