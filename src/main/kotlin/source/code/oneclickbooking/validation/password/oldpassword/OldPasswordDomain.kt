package source.code.oneclickbooking.validation.password.oldpassword

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Constraint(validatedBy = [OldPasswordValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class OldPasswordDomain (
    val message: String = "If you are changing your password, you must provide the old password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)