package source.code.oneclickbooking.integration.dto

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.jdbc.SqlMergeMode
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.dto.request.UserUpdateDto


@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql"
    ]
)
@SpringBootTest
class UserUpdateDtoValidationTest {
    private lateinit var validator: Validator

    @Autowired
    fun setValidator(validator: Validator) {
        this.validator = validator
    }

    @Test
    @DisplayName("should validate valid partial UserUpdateDto")
    fun testValidPartialUserUpdateDto() {
        LOGGER.info("Running testValidPartialUserUpdateDto...")

        val validDto = UserUpdateDto(name = "John", surname = "Doe")
        val violations = validator.validate(validDto)

        assertThat(violations.isEmpty()).isTrue

        LOGGER.info("testValidPartialUserUpdateDto passed!")
    }

    @Test
    @DisplayName("should validate valid UserUpdateDto with password change")
    fun testValidUserUpdateDtoWithPasswordChange() {
        LOGGER.info("Running testValidUserUpdateDtoWithPasswordChange...")

        val validDto = UserUpdateDto(password = "Dimas@123", oldPassword = "Dimas@123")

        val violations = validator.validate(validDto)

        assertThat(violations.isEmpty()).isTrue

        LOGGER.info("testValidUserUpdateDtoWithPasswordChange passed!")
    }

    @Test
    @DisplayName("should validate valid UserUpdateDto with email change")
    @SqlGroup(
       Sql(
           value = ["classpath:testcontainers/insert-user.sql"],
           executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
       ),
       Sql(
           value = ["classpath:testcontainers/remove-user.sql"],
           executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
       )
    )
    fun testValidUserUpdateDtoWithEmailChange() {
        LOGGER.info("Running testValidUserUpdateDtoWithEmailChange...")

        val validDto = UserUpdateDto(email = "email@gmail.com", oldPassword = "Dimas@123")

        val violations = validator.validate(validDto)

        assertThat(violations.isEmpty()).isTrue

        LOGGER.info("testValidUserUpdateDtoWithEmailChange passed!")
    }

    @Test
    @DisplayName("should fail validation when email is not unique")
    @SqlGroup(
        Sql(
            value = ["classpath:testcontainers/insert-user.sql"],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        Sql(
            value = ["classpath:testcontainers/remove-user.sql"],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun testInvalidEmailForUserUpdateDto() {
        LOGGER.info("Running testInvalidEmailForUserUpdateDto...")

        val alreadyTakenEmail = "test_email1@gmail.com"

        val invalidDto = UserUpdateDto(email = alreadyTakenEmail, oldPassword = "Dimas@123")

        val violations = validator.validate(invalidDto)

        assertAndLogViolations(violations)
        assertThat(violations.any { it.propertyPath.toString() == "email" }).isTrue

        LOGGER.info("testInvalidEmailForUserUpdateDto passed!")
    }

    @Test
    @DisplayName("should fail validation when email is present, but oldpassword is not")
    @SqlGroup(
        Sql(
            value = ["classpath:testcontainers/insert-user.sql"],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        Sql(
            value = ["classpath:testcontainers/remove-user.sql"],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun testValidEmailButNoOldPasswordForUserUpdateDto() {
        LOGGER.info("Running testValidEmailButNoOldPasswordForUserUpdateDto...")

        val invalidDto = UserUpdateDto(email = "email@gmail.com")

        val violations = validator.validate(invalidDto)

        assertAndLogViolations(violations)
        assertThat(violations.any {
            it.constraintDescriptor.annotation.annotationClass.simpleName == "EmailChangeRequireOldPassword"
        }).isTrue

        LOGGER.info("testValidEmailButNoOldPasswordForUserUpdateDto passed!")
    }

    @Test
    @DisplayName("should fail validation when password is present, but oldpassword is not")
    @SqlGroup(
        Sql(
            value = ["classpath:testcontainers/insert-user.sql"],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        Sql(
            value = ["classpath:testcontainers/remove-user.sql"],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun testValidPasswordButNoOldPasswordForUserUpdateDto() {
        LOGGER.info("Running testValidPasswordButNoOldPasswordForUserUpdateDto...")

        val invalidDto = UserUpdateDto(password = "Dimas@123")

        val violations = validator.validate(invalidDto)

        assertAndLogViolations(violations)
        assertThat(violations.any {
            it.constraintDescriptor.annotation.annotationClass.simpleName == "PasswordChangeRequireOldPassword"
        }).isTrue

        LOGGER.info("testValidPasswordButNoOldPasswordForUserUpdateDto passed!")
    }

    private fun assertAndLogViolations(violations: Set<ConstraintViolation<UserUpdateDto>>) {
        assertThat(violations.isNotEmpty()).isTrue
        val violationsSimpleNames = violations.map {
            it.constraintDescriptor.annotation.annotationClass.simpleName
        }

        LOGGER.info("Violations: $violationsSimpleNames")
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(UserUpdateDtoValidationTest::class.java)
    }
}