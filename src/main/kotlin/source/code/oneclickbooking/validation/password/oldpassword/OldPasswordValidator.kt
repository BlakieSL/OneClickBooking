package source.code.oneclickbooking.validation.password.oldpassword

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import source.code.oneclickbooking.dto.request.UserUpdateDto

class OldPasswordValidator : ConstraintValidator<OldPasswordDomain, UserUpdateDto> {
    override fun isValid(dto: UserUpdateDto?, context: ConstraintValidatorContext?): Boolean =
        dto?.let {
            (it.password != null) == (it.oldPassword != null)
        } ?: true
}