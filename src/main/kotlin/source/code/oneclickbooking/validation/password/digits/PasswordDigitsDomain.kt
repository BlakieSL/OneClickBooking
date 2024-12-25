package source.code.oneclickbooking.validation.password.digits

import jakarta.validation.Constraint
import jakarta.validation.Payload
import source.code.oneclickbooking.helper.ExceptionMessages
import kotlin.reflect.KClass

@Constraint(validatedBy = [PasswordDigitsValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordDigitsDomain (
    val message: String = "{${ExceptionMessages.PASSWORD_DIGITS}}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)