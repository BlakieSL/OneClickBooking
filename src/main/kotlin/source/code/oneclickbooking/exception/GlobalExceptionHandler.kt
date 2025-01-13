package source.code.oneclickbooking.exception


import jakarta.validation.ValidationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import source.code.oneclickbooking.config.MessageResolverHolder
import source.code.oneclickbooking.helper.ExceptionMessages

@ControllerAdvice
class GlobalExceptionHandler() {
    @ExceptionHandler(RecordNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleRecordNotFoundException(ex: RecordNotFoundException): String? {
        return ex.message
    }

    @ExceptionHandler(ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleValidationException(ex: ValidationException): String? {
        return MessageResolverHolder.messageResolver.getMessage(
            key = ExceptionMessages.VALIDATION,
            args = arrayOf(ex.message ?: "VALIDATION ERROR")
        )
    }

    @ExceptionHandler(InvalidFilterKeyException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleInvalidFilterKeyException(ex: InvalidFilterKeyException): String? {
        return ex.message
    }

    @ExceptionHandler(InvalidFilterOperationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleInvalidFilterOperationException(ex: InvalidFilterOperationException): String? {
        return ex.message
    }

    @ExceptionHandler(FileProcessingException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleFileProcessingException(ex: FileProcessingException): String? {
        return ex.message
    }

    @ExceptionHandler(LocalizedIllegalArgument::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleInternalizedIllegalArgumentException(ex: LocalizedIllegalArgument): String? {
        return ex.message
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): Map<String, String> {
        return ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "VALIDATION ERROR")
        }
    }

    @ExceptionHandler(JwtAuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun handleJwtAuthenticationException(ex: JwtAuthenticationException): String? {
        return ex.message
    }

    @ExceptionHandler(InvalidRefreshTokenException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleInvalidRefreshTokenException(ex: InvalidRefreshTokenException): String? {
        return ex.message
    }
}