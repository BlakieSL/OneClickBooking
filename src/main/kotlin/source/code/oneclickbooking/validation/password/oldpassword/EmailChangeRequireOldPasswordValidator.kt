package source.code.oneclickbooking.validation.password.oldpassword

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import source.code.oneclickbooking.dto.request.UserUpdateDto

class EmailChangeRequireOldPasswordValidator :
    ConstraintValidator<EmailChangeRequireOldPassword, UserUpdateDto> {

        override fun isValid(dto: UserUpdateDto?, context: ConstraintValidatorContext?): Boolean {
            return dto?.let {
                it.email?.let { _ -> it.oldPassword != null } ?: true
            } ?: true
        }
    }