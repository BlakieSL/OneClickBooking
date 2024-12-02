package source.code.oneclickbooking.validation.password.digits

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordDigitsValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordDigitsDomain (
    val message: String = "Password must contain at least one digit",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)