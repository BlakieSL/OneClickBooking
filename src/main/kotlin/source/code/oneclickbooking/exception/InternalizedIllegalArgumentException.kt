package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.MessageResolver

class InternalizedIllegalArgumentException(
    messageKey: String,
    vararg args: Any
) : RuntimeException(
    message = MessageResolver.getMessage(
        key = messageKey,
        args = args
    )
)