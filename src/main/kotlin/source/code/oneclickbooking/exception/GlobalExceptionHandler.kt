package source.code.oneclickbooking.exception


import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.MessageResolver

@ControllerAdvice
class GlobalExceptionHandler() {
    @ExceptionHandler(RecordNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleRecordNotFoundException(ex: RecordNotFoundException): String? {
        return ex.message
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: ValidationException): String? {
        return MessageResolver.getMessage(
            key = ExceptionMessages.VALIDATION,
            args = arrayOf(ex.message ?: "VALIDATION ERROR")
        )
    }

    @ExceptionHandler(InvalidFilterKeyException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidFilterKeyException(ex: InvalidFilterKeyException): String? {
        return ex.message
    }

    @ExceptionHandler(InvalidFilterOperationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidFilterOperationException(ex: InvalidFilterOperationException): String? {
        return ex.message
    }

    @ExceptionHandler(FileProcessingException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleFileProcessingException(ex: FileProcessingException): String? {
        return ex.message
    }

    @ExceptionHandler(InternalizedIllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInternalizedIllegalArgumentException(ex: InternalizedIllegalArgumentException): String? {
        return ex.message
    }



    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): String? {
        return ex.message
    }

    @ExceptionHandler(JwtAuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleJwtAuthenticationException(ex: JwtAuthenticationException): String? {
        return ex.message
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleInvalidRefreshTokenException(ex: InvalidRefreshTokenException): String? {
        return ex.message
    }
}