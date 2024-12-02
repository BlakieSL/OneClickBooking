package source.code.oneclickbooking.validation.password.uppercase

import jakarta.validation.Constraint
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordUppercaseValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordUppercaseDomain(
    val message: String = "Password must contain at least one uppercase letter",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)
