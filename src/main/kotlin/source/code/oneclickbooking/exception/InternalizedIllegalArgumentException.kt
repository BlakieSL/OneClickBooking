package source.code.oneclickbooking.exception

class InternalizedIllegalArgumentException(
    messageKey: String,
    vararg args: Any
) : LocalizedException(messageKey = messageKey, args = args)