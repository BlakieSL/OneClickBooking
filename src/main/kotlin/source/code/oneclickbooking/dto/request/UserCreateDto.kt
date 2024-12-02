package source.code.oneclickbooking.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import source.code.oneclickbooking.model.User.Companion.EMAIL_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.NAME_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.PASSWORD_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.PASSWORD_MIN_LENGTH
import source.code.oneclickbooking.model.User.Companion.SURNAME_MAX_LENGTH
import source.code.oneclickbooking.validation.password.digits.PasswordDigitsDomain
import source.code.oneclickbooking.validation.password.lowercase.PasswordLowercaseDomain
import source.code.oneclickbooking.validation.password.special.PasswordSpecialDomain
import source.code.oneclickbooking.validation.password.uppercase.PasswordUppercaseDomain

data class UserCreateDto(
    @field:NotBlank
    @field:Size(max = NAME_MAX_LENGTH)
    val name: String,

    @field:NotBlank
    @field:Size(max = SURNAME_MAX_LENGTH)
    val surname: String,

    @field:NotBlank
    @field:Size(max = EMAIL_MAX_LENGTH)
    @field:Email
    val email: String,

    @field:NotBlank
    @field:Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @field:PasswordDigitsDomain
    @field:PasswordLowercaseDomain
    @field:PasswordUppercaseDomain
    @field:PasswordSpecialDomain
    val password: String
)