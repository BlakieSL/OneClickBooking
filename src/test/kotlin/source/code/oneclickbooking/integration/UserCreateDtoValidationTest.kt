package source.code.oneclickbooking.integration

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
import source.code.oneclickbooking.dto.request.UserCreateDto

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
class UserCreateDtoValidationTest {
    private lateinit var validator: Validator

    @Autowired
    fun setValidator(validator: Validator) {
        this.validator = validator
    }

    @Test
    @DisplayName("should validate valid UserCreateDto")
    @SqlGroup(
        Sql(
            value = ["classpath:testcontainers/add-chuck.sql"],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        Sql(
            value = ["classpath:testcontainers/remove-chuck.sql"],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun testValidUserCreateDto() {
        LOGGER.info("Running testValidUserCreateDto...")

        val validDto = UserCreateDto.createDefault()
        val violations = validator.validate(validDto)

        assertThat(violations.isEmpty()).isTrue

        LOGGER.info("testValidUserCreateDto passed!")
    }

    @Test
    @DisplayName("should fail validation when email is not unique")
    @SqlGroup(
        Sql(
            value = ["classpath:testcontainers/add-chuck.sql"],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        Sql(
            value = ["classpath:testcontainers/remove-chuck.sql"],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun testInvalidEmailForUserCreateDto() {
        LOGGER.info("Running testInvalidCreateDto...")

        val alreadyTakenEmail = "test_email1@gmail.com"
        val invalidDto = UserCreateDto.createDefault(email = alreadyTakenEmail)

        val violations = validator.validate(invalidDto)

        assertAndLogViolations(violations)
        assertThat(violations.any { it.propertyPath.toString() == "email" }).isTrue

        LOGGER.info("testInvalidEmailForUserCreateDto passed!")
    }

    @Test
    @DisplayName("should fail validation when password fails some constraints")
    fun testInvalidPasswordForUserCreateDto() {
        LOGGER.info("Running testInvalidPasswordForUserCreateDto...")

        val invalidDto = UserCreateDto.createDefault(password = "password")

        val violations = validator.validate(invalidDto)

        assertAndLogViolations(violations)
        assertThat(violations.any {
            it.constraintDescriptor.annotation.annotationClass.simpleName == "PasswordDigitsDomain"
        }).isTrue
        assertThat(violations.any {
            it.constraintDescriptor.annotation.annotationClass.simpleName == "PasswordUppercaseDomain"
        }).isTrue
        assertThat(violations.any {
            it.constraintDescriptor.annotation.annotationClass.simpleName == "PasswordSpecialDomain"
        }).isTrue

        LOGGER.info("testInvalidPasswordForUserCreateDto passed!")
    }

    private fun assertAndLogViolations(violations: Set<ConstraintViolation<UserCreateDto>>) {
        assertThat(violations.isNotEmpty()).isTrue
        val violationsSimpleNames = violations.map {
            it.constraintDescriptor.annotation.annotationClass.simpleName
        }

        LOGGER.info("Violations: $violationsSimpleNames")
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(UserCreateDtoValidationTest::class.java)
    }
}