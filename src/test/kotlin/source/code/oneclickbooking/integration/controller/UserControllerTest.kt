package source.code.oneclickbooking.integration.controller

import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.implementation.util.JwtService
import java.time.Instant
import java.util.*

@ActiveProfiles("test")
@Testcontainers
@Sql(
    value = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql",
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
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
    @DisplayName("GET /api/users/{id} - Should return user, When user is AccountOwner")
    fun `test get user as AccountOwner`() {
        logRunning()

        setUserContext(1)
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(1),
                jsonPath("$.name").value("test_name1"),
                jsonPath("$.surname").value("test_surname1"),
                jsonPath("$.email").value("test_email1@gmail.com")
            )

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("GET /api/users/{id} - Should return user, When user is Admin")
    fun `test get user as Admin`() {
        logRunning()

        setAdminContext(1)
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(1),
                jsonPath("$.name").value("test_name1"),
                jsonPath("$.surname").value("test_surname1"),
                jsonPath("$.email").value("test_email1@gmail.com")
            )

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("GET /api/users/{id} - Should return 403, When user is not AccountOwner or Admin")
    fun `test get user as NotAccountOwnerOrAdmin`() {
        logRunning()

        setUserContext(2)
        mockMvc.perform(get("/api/users/1")).andExpect(status().isForbidden)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/register - Should create user, When user is anonymous")
    fun `test create user as Anonymous`() {
        logRunning()

        val requestBody = """
            {
                "name": "test_name4",
                "surname": "test_surname4",
                "email": "test_email4@gmail.com",
                "password": "Dimas@123"
            }
        """.trimIndent()

        mockMvc.perform(post("/api/users/register")
            .contentType("application/json")
            .content(requestBody))
            .andExpect(status().isCreated)
            .andExpectAll(
                jsonPath("$.id").value(4),
                jsonPath("$.name").value("test_name4"),
                jsonPath("$.surname").value("test_surname4"),
                jsonPath("$.email").value("test_email4@gmail.com")
            )

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/register - Should return 403, When user is not anonymous")
    fun `test create user as NotAnonymous`() {
        logRunning()

        setUserContext(1)
        val requestBody = """
            {
                "name": "test_name4",
                "surname": "test_surname4",
                "email": "test_email4@gmail.com",
                "password": "Dimas@123"
            }
        """.trimIndent()

        mockMvc.perform(post("/api/users/register")
            .contentType("application/json")
            .content(requestBody))
            .andExpect(status().isForbidden)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/register - Should return 400, When email is not unique")
    fun `test create user not unique email`() {
        logRunning()

        val requestBody = """
            {
                "name": "test_name1",
                "surname": "test_surname1",
                "email": "test_email1@gmail.com",
                "password": "Dimas@123"
            }
            """.trimIndent()

        mockMvc.perform(post("/api/users/register"))
            .andExpectAll(
                status().isBadRequest
            )

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/users/{id} - Should delete user, When user is AccountOwner")
    fun `test delete user as AccountOwner`() {
        logRunning()

        setUserContext(1)
        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/users/{id} - Should delete user, When user is Admin")
    fun `test delete user as Admin`() {
        logRunning()

        setAdminContext(1)
        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isNoContent)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/users/{id} - Should return 403, When user is not AccountOwner or Admin")
    fun `test delete user as NotAccountOwnerOrAdmin`() {
        logRunning()

        setUserContext(2)
        mockMvc.perform(delete("/api/users/1"))
            .andExpect(status().isForbidden)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/users/{id} - Should return 404, When user not found")
    fun `test delete user not found`() {
        logRunning()

        setAdminContext(1)
        mockMvc.perform(delete("/api/users/999"))
            .andExpect(status().isNotFound)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update user, When user is AccountOwner")
    fun `test update user as AccountOwner`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update user, When user is Admin")
    fun `test update user as Admin`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 403, When user is not AccountOwner or Admin")
    fun `test update user as NotAccountOwnerOrAdmin`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 404, When user not found")
    fun `test update user not found`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update email")
    fun `test update email`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, When email is not unique")
    fun `test update email not unique`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, When updating email and" +
            " not providing old password")
    fun `test update email without old password`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, updating email and" +
            " old password is incorrect")
    fun `test update email incorrect old password`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should update password")
    fun `test update password`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, When updating password and" +
            " not providing old password")
    fun `test update password without old password`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("PATCH /api/users/{id} - Should return 400, updating password and" +
            " old password is incorrect")
    fun `test update password incorrect old password`() {
        logRunning()

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

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/refresh-token - Should return new access token")
    fun `test refresh token`() {
        val validRefreshToken = jwtService.createRefreshToken(
            "testuser",
            1,
            listOf("ROLE_USER")
        )

        val requestDto = """
            {
                "refreshToken": "$validRefreshToken"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/users/refresh-token")
                .contentType("application/json")
                .content(requestDto)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.accessToken").isNotEmpty)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/refresh-token - Should return 400, When token is invalid")
    fun `test refresh token invalid`() {
        val invalidToken = "thisIsNotAValidJWT"

        val requestDto = """
            {
                "refreshToken": "$invalidToken"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/users/refresh-token")
                .contentType("application/json")
                .content(requestDto)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/refresh-token - Should return 400, When token is expired")
    fun `test refresh token expired`() {
        val validToken = jwtService.createRefreshToken(
            "testuser",
            1,
            listOf("ROLE_USER")
        )

        val signedJWT = JWTParser.parse(validToken) as SignedJWT
        val expiredClaimsSet = JWTClaimsSet.Builder(signedJWT.jwtClaimsSet)
            .expirationTime(Date.from(Instant.now().minusSeconds(60)))
            .build()

        val expiredToken = SignedJWT(signedJWT.header, expiredClaimsSet)
        expiredToken.sign(MACSigner(sharedKey.toByteArray()))

        val requestDto = """
            {
                "refreshToken": "${expiredToken.serialize()}"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/users/refresh-token")
                .contentType("application/json")
                .content(requestDto)
        ).andExpect(status().isUnauthorized)
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/refresh-token - Should return 400, When invalid signature")
    fun `test refresh token invalid signature`() {
        val validToken = jwtService.createRefreshToken(
            "testuser",
            1,
            listOf("ROLE_USER")
        )

        val signedJWT = JWTParser.parse(validToken) as SignedJWT

        val tamperedClaimsSet = JWTClaimsSet.Builder(signedJWT.jwtClaimsSet)
            .claim("tamperedClaim", "tamperedValue")
            .build()

        val tamperedToken = SignedJWT(signedJWT.header, tamperedClaimsSet)
        tamperedToken.sign(MACSigner("INVALID_SHARED_KESasfdasfddASFSDFSDFSDF".toByteArray()))

        val requestDto = """
            {
                "refreshToken": "${tamperedToken.serialize()}"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/users/refresh-token")
                .contentType("application/json")
                .content(requestDto)
        ).andExpect(status().isUnauthorized)
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/refresh-token - Should return 400, When missing claims")
    fun `test refresh token missing claims`() {
        val validToken = jwtService.createRefreshToken(
            "testuser",
            1,
            listOf("ROLE_USER")
        )

        val signedJWT = JWTParser.parse(validToken) as SignedJWT
        val missingClaimsSet = JWTClaimsSet.Builder()
            .expirationTime(Date.from(Instant.now().plusSeconds(60)))
            .build()

        val tokenWithMissingClaims = SignedJWT(signedJWT.header, missingClaimsSet)
        tokenWithMissingClaims.sign(MACSigner(sharedKey.toByteArray()))

        val requestDto = """
            {
                "refreshToken": "${tokenWithMissingClaims.serialize()}"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/users/refresh-token")
                .contentType("application/json")
                .content(requestDto)
        ).andExpect(status().isBadRequest)
    }

    private fun setUserContext(userId: Int) {
        setContext(userId, "ROLE_USER")
    }

    private fun setAdminContext(userId: Int) {
        setContext(userId, "ROLE_ADMIN")
    }

    private fun setContext(userId: Int, role: String) {
        val customAuth = CustomAuthenticationToken(
            principal = "testuser",
            userId = userId,
            credentials = null,
            authorities = listOf(SimpleGrantedAuthority(role))
        )
        SecurityContextHolder.getContext().authentication = customAuth
    }

    private fun logRunning() {
        LOGGER.info("RUNNING...")
    }

    private fun logPassed() {
        LOGGER.info("PASSED!")
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BookingControllerTest::class.java)
    }
}