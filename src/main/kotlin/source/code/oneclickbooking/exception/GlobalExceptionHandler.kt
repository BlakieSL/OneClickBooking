package source.code.oneclickbooking.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleRecordNotFoundException(ex: RecordNotFoundException): String? {
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
}