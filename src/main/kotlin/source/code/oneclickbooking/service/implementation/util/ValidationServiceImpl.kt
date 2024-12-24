package source.code.oneclickbooking.service.implementation.util

import jakarta.validation.ConstraintViolation
import jakarta.validation.ValidationException
import jakarta.validation.Validator
import org.springframework.stereotype.Service
import source.code.oneclickbooking.service.declaration.util.ValidationService

@Service
class ValidationServiceImpl(private val validator: Validator) : ValidationService {
    override fun <T> validate(dto: T, vararg groups: Class<*>) {
        val errors: Set<ConstraintViolation<T>> = validator.validate(dto, *groups)
        if(errors.isNotEmpty()) {
            val errorMessages = errors.joinToString(", ") {
                "${it.propertyPath} ${it.message} ${it.invalidValue}"
            }
            throw ValidationException(errorMessages)
        }
    }
}