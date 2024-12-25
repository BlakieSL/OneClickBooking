package source.code.oneclickbooking.validation.email

import jakarta.validation.Constraint
import jakarta.validation.Payload
import source.code.oneclickbooking.helper.ExceptionMessages
import kotlin.reflect.KClass

@Constraint(validatedBy = [UniqueEmailValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class UniqueEmailDomain (
    val message: String = "{${ExceptionMessages.UNIQUE_EMAIL}}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)