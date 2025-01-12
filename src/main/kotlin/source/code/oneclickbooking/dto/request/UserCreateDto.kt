package source.code.oneclickbooking.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import source.code.oneclickbooking.model.User.Companion.EMAIL_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.NAME_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.PASSWORD_MAX_LENGTH
import source.code.oneclickbooking.model.User.Companion.PASSWORD_MIN_LENGTH
import source.code.oneclickbooking.model.User.Companion.SURNAME_MAX_LENGTH
import source.code.oneclickbooking.validation.email.UniqueEmailDomain
import source.code.oneclickbooking.validation.password.digits.PasswordDigitsDomain
import source.code.oneclickbooking.validation.password.lowercase.PasswordLowercaseDomain
import source.code.oneclickbooking.validation.password.special.PasswordSpecialDomain
import source.code.oneclickbooking.validation.password.uppercase.PasswordUppercaseDomain

data class UserCreateDto(
    @JsonProperty(required = true)
    @field:NotBlank
    @field:Size(max = NAME_MAX_LENGTH)
    val name: String,

    @JsonProperty(required = true)
    @field:NotBlank
    @field:Size(max = SURNAME_MAX_LENGTH)
    val surname: String,

    @JsonProperty(required = true)
    @field:NotBlank
    @field:Size(max = EMAIL_MAX_LENGTH)
    @field:Email
    @field:UniqueEmailDomain
    val email: String,

    @JsonProperty(required = true)
    @field:NotBlank
    @field:Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @field:PasswordDigitsDomain
    @field:PasswordLowercaseDomain
    @field:PasswordUppercaseDomain
    @field:PasswordSpecialDomain
    val password: String
) {
    companion object {
        fun createDefault(
            name: String = "John",
            surname: String = "Doe",
            email: String = "email@gmail.com",
            password: String = "Dimas@123"
        ) : UserCreateDto {
            return UserCreateDto(
                name = name,
                surname = surname,
                email = email,
                password = password
            )
        }
    }
}