package source.code.oneclickbooking.validation.password.lowercase

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordLowercaseValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordLowercaseDomain(
    val message: String = "Password must contain at least one lowercase letter",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
