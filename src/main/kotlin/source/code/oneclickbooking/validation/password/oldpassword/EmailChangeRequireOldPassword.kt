package source.code.oneclickbooking.validation.password.oldpassword

import jakarta.validation.Constraint
import jakarta.validation.Payload
import source.code.oneclickbooking.helper.ExceptionMessages
import kotlin.reflect.KClass

@Constraint(validatedBy = [EmailChangeRequireOldPasswordValidator::class])
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class EmailChangeRequireOldPassword(
    val message: String = "{${ExceptionMessages.EMAIL_CHANGE}}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
