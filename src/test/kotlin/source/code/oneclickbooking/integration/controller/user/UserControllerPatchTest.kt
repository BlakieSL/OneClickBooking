package source.code.oneclickbooking.integration.controller.user

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.Utils.setAdminContext
import source.code.oneclickbooking.integration.Utils.setUserContext
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.implementation.util.JwtService

@ActiveProfiles("test")
@Testcontainers
@Sql(
    value = [
        "classpath:testcontainers/schema/drop-schema.sql",
        "classpath:testcontainers/schema/create-schema.sql",
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerPatchTest {
    private lateinit var passwordEncoder: PasswordEncoder
    private lateinit var mockMvc: MockMvc
    private lateinit var userRepository: UserRepository
    private lateinit var jwtService: JwtService
    @Value("\${spring.jws.sharedKey}")
    private lateinit var sharedKey: String

    @Autowired
    fun setPasswordEncoder(passwordEncoder: PasswordEncoder) {
        this.passwordEncoder = passwordEncoder
    }

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Autowired
    fun setUserRepository(userRepository: UserRepository) {
        this.userRepository = userRepository
    }

    @Autowired
    fun setJwtService(jwtService: JwtService) {
        this.jwtService = jwtService
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update user, When user is AccountOwner")
    fun `test update user as AccountOwner`() {
        setUserContext(1)
        val requestBody = """
            {
                "name": "updated_name",
                "surname": "updated_surname"
            }
         """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(1),
            jsonPath("$.name").value("updated_name"),
            jsonPath("$.surname").value("updated_surname"),
            jsonPath("$.email").value("test_email1@gmail.com")
        )
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update user, When user is Admin")
    fun `test update user as Admin`() {
        setAdminContext(3)
        val requestBody = """
            {
                "name": "updated_name",
                "surname": "updated_surname"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(1),
            jsonPath("$.name").value("updated_name"),
            jsonPath("$.surname").value("updated_surname"),
            jsonPath("$.email").value("test_email1@gmail.com")
        )
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 403, When user is not AccountOwner or Admin")
    fun `test update user as NotAccountOwnerOrAdmin`() {
        setUserContext(2)
        val requestBody = """
            {
                "name": "updated_name",
                "surname": "updated_surname"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isForbidden)
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 404, When user not found")
    fun `test update user not found`() {
        setAdminContext(3)
        val requestBody = """
            {
                "name": "updated_name",
                "surname": "updated_surname"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/999")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isNotFound)
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update email")
    fun `test update email`() {
        setUserContext(1)
        val requestBody = """
            {
                "email": "updated_email@gmail.com",
                "oldPassword": "Dimas@123"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(1),
            jsonPath("$.email").value("updated_email@gmail.com")
        )
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, When email is not unique")
    fun `test update email not unique`() {
        setUserContext(1)
        val requestBody = """
            {
                "email": "test_email1@gmail.com",
                "oldPassword": "Dimas@123"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, When updating email and" +
            " not providing old password")
    fun `test update email without old password`() {
        setUserContext(1)
        val requestBody = """
            {
                "email": "test_email1@gmail.com"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, updating email and" +
            " old password is incorrect")
    fun `test update email incorrect old password`() {
        setUserContext(1)
        val requestBody = """
            {
                "email": "test_email1@gmail.com",
                "oldPassword": "INCORRECT"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update password")
    fun `test update password`() {
        setUserContext(1)
        val requestBody = """
            {
                "password": "Dimas@12345",
                "oldPassword": "Dimas@123"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(1)
        )

        val updatedUser = userRepository.findById(1).get()

        assertThat(passwordEncoder.matches("Dimas@12345", updatedUser.password)).isTrue()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, When updating password and" +
            " not providing old password")
    fun `test update password without old password`() {
        setUserContext(1)
        val requestBody = """
            {
                "password": "Dimas@1234",
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, updating password and" +
            " old password is incorrect")
    fun `test update password incorrect old password`() {
        setUserContext(1)
        val requestBody = """
            {
                "password": "Dimas@1234",
                "oldPassword": "INCORRECT"
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/users/1")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }
}