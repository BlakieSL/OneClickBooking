package source.code.oneclickbooking.dto

import jakarta.validation.Validation
import org.junit.jupiter.api.Test
import source.code.oneclickbooking.dto.request.UserCreateDto
import kotlin.test.assertTrue

class UserCreateDtoTest {
    private val validator = Validation.buildDefaultValidatorFactory().validator
    @Test
    fun `should validate valid UserCreateDto`() {
        val dto = UserCreateDto.createDefault()
        val violations = validator.validate(dto)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should fail validation when email is invalid`() {
        val dto = UserCreateDto.createDefault(email = "invalid-email")
        val violations = validator.validate(dto)
        assertTrue(violations.isNotEmpty())
        assertTrue(violations.any { it.propertyPath.toString() == "email" })
    }

    @Test
    fun `should fail validation when password has no digits`() {
        val dto = UserCreateDto.createDefault(password = "NoDigitsPassword!")
        val violations = validator.validate(dto)
        assertTrue(violations.isNotEmpty())
        assertTrue(violations.any { it.propertyPath.toString() == "password" })
    }

    @Test
    fun `should fail validation when password has no lowercase letters`() {
        val dto = UserCreateDto.createDefault(password = "PASSWORD1!")
        val violations = validator.validate(dto)
        assertTrue(violations.isNotEmpty())
        assertTrue(violations.any { it.propertyPath.toString() == "password" })
    }

    @Test
    fun `should fail validation when password has no uppercase letters`() {
        val dto = UserCreateDto.createDefault(password = "password1!")
        val violations = validator.validate(dto)
        assertTrue(violations.isNotEmpty())
        assertTrue(violations.any { it.propertyPath.toString() == "password" })
    }

    @Test
    fun `should fail validation when password has no special characters`() {
        val dto = UserCreateDto.createDefault(password = "Password1")
        val violations = validator.validate(dto)
        assertTrue(violations.isNotEmpty())
        assertTrue(violations.any { it.propertyPath.toString() == "password" })
    }

    @Test
    fun `should fail validation when password violates 3 constrains at once`() {
        val dto = UserCreateDto.createDefault(password = "password")
        val violations = validator.validate(dto)
        assertTrue(violations.size == 3)
        assertTrue(violations.all { it.propertyPath.toString() == "password" })
    }
}