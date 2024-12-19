package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import source.code.oneclickbooking.model.User.Companion.EMAIL_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.NAME_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.PASSWORD_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.PASSWORD_MIN_LENGTH
import source.code.oneclickbooking.model.User.Companion.SURNAME_MAX_LENGTH
import source.code.oneclickbooking.validation.email.UniqueEmailDomain
import source.code.oneclickbooking.validation.password.digits.PasswordDigitsDomain
import source.code.oneclickbooking.validation.password.lowercase.PasswordLowercaseDomain
import source.code.oneclickbooking.validation.password.oldpassword.EmailChangeRequireOldPassword
import source.code.oneclickbooking.validation.password.oldpassword.PasswordChangeRequireOldPassword
import source.code.oneclickbooking.validation.password.special.PasswordSpecialDomain
import source.code.oneclickbooking.validation.password.uppercase.PasswordUppercaseDomain

@PasswordChangeRequireOldPassword
@EmailChangeRequireOldPassword
data class UserUpdateDto(
    @field:Size(max = NAME_MAX_LENGTH)
    val name: String? = null,

    @field:Size(max = SURNAME_MAX_LENGTH)
    val surname: String? = null,

    @field:Size(max = EMAIL_MAX_LENGTH)
    @field:Email
    @field:UniqueEmailDomain
    val email: String? = null,

    @field:Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @field:PasswordDigitsDomain
    @field:PasswordLowercaseDomain
    @field:PasswordUppercaseDomain
    @field:PasswordSpecialDomain
    val password: String? = null,

    val oldPassword: String? = null,
) {
    companion object {
        fun createDefault(
            name: String? = null,
            surname: String? = null,
            email: String? = null,
            password: String? = null,
            oldPassword: String? = null
        ): UserUpdateDto {
            return UserUpdateDto(
                name = name,
                surname = surname,
                email = email,
                password = password,
                oldPassword = oldPassword
            )
        }
    }
}
