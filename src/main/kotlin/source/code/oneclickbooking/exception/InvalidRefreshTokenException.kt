package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.MessageResolver

class InvalidRefreshTokenException(
    messageKey: String,
    vararg args: Any
) : RuntimeException(MessageResolver.getMessage(messageKey, args))