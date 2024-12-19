package source.code.oneclickbooking.validation.password.oldpassword

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass


@Constraint(validatedBy = [PasswordChangeRequireOldPasswordValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class PasswordChangeRequireOldPassword (
    val message: String = "If you are changing your password, you must provide the old password",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)