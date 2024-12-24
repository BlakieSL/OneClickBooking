package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.MessageResolver

class FileProcessingException(
    cause: Throwable? = null
): RuntimeException(
    message = MessageResolver.getMessage(
        key = ExceptionMessages.FILE_PROCESSING,
        args = arrayOf(cause ?: "FILE PROCESSING ERROR")
    )
)