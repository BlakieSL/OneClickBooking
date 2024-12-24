package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.MessageResolver

class InternalizedIllegalArgumentException(
    messageKey: String,
    vararg args: Any
) : RuntimeException(MessageResolver.getMessage(key = messageKey, args = args))