package source.code.oneclickbooking.validation.password.special

import jakarta.validation.Constraint
import source.code.oneclickbooking.helper.ExceptionMessages
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordSpecialValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordSpecialDomain(
    val message: String = ExceptionMessages.PASSWORD_SPECIAL,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
