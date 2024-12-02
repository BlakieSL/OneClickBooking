package source.code.oneclickbooking.validation.password.special

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordSpecialValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordSpecialDomain(
    val message: String = "Password must contain at least one special character",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
