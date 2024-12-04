package source.code.oneclickbooking.validation.email

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [UniqueEmailValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class UniqueEmailDomain (
    val message: String = "Email is already in use",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)