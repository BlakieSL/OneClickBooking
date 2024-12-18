package source.code.oneclickbooking.integration.dto

import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import source.code.oneclickbooking.dto.request.UserUpdateDto

class UserUpdateDtoTest {
    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should validate when both password and oldPassword are null`() {
        val dto = UserUpdateDto()
        val violations = validator.validate(dto)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should validate when both password and oldPassword are provided`() {
        val dto = UserUpdateDto(password = "NewPassword1!", oldPassword = "OldPassword1!")
        val violations = validator.validate(dto)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should fail validation when password is provided but oldPassword is null`() {
        val dto = UserUpdateDto(password = "NewPassword1!", oldPassword = null)
        val violations = validator.validate(dto)
        println(violations);
        assertTrue(violations.isNotEmpty())
    }

    @Test
    fun `should fail validation when oldPassword is provided but password is null`() {
        val dto = UserUpdateDto(password = null, oldPassword = "OldPassword1!")
        val violations = validator.validate(dto)
        assertTrue(violations.isNotEmpty())
    }
}