package source.code.oneclickbooking.validation.password.uppercase

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordUppercaseValidator : ConstraintValidator<PasswordUppercaseDomain, String> {
    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean =
        password.isNullOrEmpty() || password.matches(".*[A-Z].*".toRegex())
}