package source.code.oneclickbooking.model

import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UserTest {
    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should validate valid user`() {
        val user = User.createDefault()
        val violations = validator.validate(user)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should fail validation when not correct email`() {
        val user = User.createDefault(email = "invalid-email")
        val violations = validator.validate(user)
        assertTrue(violations.isNotEmpty())
        assertTrue(violations.any { it.propertyPath.toString() == "email" })
    }
}