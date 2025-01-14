package source.code.oneclickbooking.exception

import source.code.oneclickbooking.utils.ExceptionMessages

class FileProcessingException(
    cause: Throwable? = null
): LocalizedException(
    messageKey = ExceptionMessages.FILE_PROCESSING,
    cause = cause
)