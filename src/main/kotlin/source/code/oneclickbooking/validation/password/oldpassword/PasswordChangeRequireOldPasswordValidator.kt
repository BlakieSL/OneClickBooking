package source.code.oneclickbooking.validation.password.oldpassword

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import source.code.oneclickbooking.dto.request.UserUpdateDto

class PasswordChangeRequireOldPasswordValidator :
    ConstraintValidator<PasswordChangeRequireOldPassword, UserUpdateDto> {

        override fun isValid(dto: UserUpdateDto?, context: ConstraintValidatorContext?): Boolean {
            return dto?.let {
                it.password?.let { _ -> it.oldPassword != null } ?: true
            } ?: true
        }
    }