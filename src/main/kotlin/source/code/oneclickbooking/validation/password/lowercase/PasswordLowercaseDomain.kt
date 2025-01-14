package source.code.oneclickbooking.validation.password.lowercase

import jakarta.validation.Constraint
import jakarta.validation.Payload
import source.code.oneclickbooking.utils.ExceptionMessages
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordLowercaseValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordLowercaseDomain(
    val message: String = "{${ExceptionMessages.PASSWORD_LOWERCASE}}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
