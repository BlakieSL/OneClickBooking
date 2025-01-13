package source.code.oneclickbooking.exception

class LocalizedIllegalArgument(
    messageKey: String,
    vararg args: Any
) : LocalizedException(messageKey = messageKey, args = args)