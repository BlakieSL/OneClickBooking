package source.code.oneclickbooking.validation.password.lowercase

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordLowercaseValidator : ConstraintValidator<PasswordLowercaseDomain, String> {
    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean =
        password.isNullOrEmpty() || password.matches(".*[a-z].*".toRegex())
}