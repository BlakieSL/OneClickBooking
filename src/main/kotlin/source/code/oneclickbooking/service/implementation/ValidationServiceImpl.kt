package source.code.oneclickbooking.service.implementation

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator
import org.springframework.stereotype.Service
import source.code.oneclickbooking.service.declaration.ValidationService

@Service
class ValidationServiceImpl(private val validator: Validator) : ValidationService {
    override fun <T> validate(dto: T, vararg groups: Class<*>) {
        val errors: Set<ConstraintViolation<T>> = validator.validate(dto, *groups)
        if(errors.isNotEmpty()) {
            println("Validation errors:")
            errors.forEach {
                println("${it.propertyPath} ${it.message} ${it.invalidValue}")
            }
            throw IllegalArgumentException("Validation failed")
        }
    }
}