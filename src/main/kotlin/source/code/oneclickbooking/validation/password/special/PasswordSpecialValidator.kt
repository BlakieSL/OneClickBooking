package source.code.oneclickbooking.validation.password.special

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordSpecialValidator : ConstraintValidator<PasswordSpecialDomain, String> {
    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean =
        password.isNullOrEmpty() || password.matches(".*[^a-zA-Z0-9].*".toRegex())
}