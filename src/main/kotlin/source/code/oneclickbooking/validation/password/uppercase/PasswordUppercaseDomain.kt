package source.code.oneclickbooking.validation.password.uppercase

import jakarta.validation.Constraint
import source.code.oneclickbooking.utils.ExceptionMessages
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordUppercaseValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordUppercaseDomain(
    val message: String = "{${ExceptionMessages.PASSWORD_UPPERCASE}}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
