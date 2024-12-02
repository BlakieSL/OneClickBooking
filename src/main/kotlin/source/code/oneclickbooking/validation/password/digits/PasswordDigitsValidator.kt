package source.code.oneclickbooking.validation.password.digits

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PasswordDigitsValidator : ConstraintValidator<PasswordDigitsDomain, String> {
    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean =
        password.isNullOrEmpty() || password.matches(".*\\d.*".toRegex())
}