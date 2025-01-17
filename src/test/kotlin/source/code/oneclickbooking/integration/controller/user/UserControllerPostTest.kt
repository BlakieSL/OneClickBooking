package source.code.oneclickbooking.integration.controller.user

import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.Utils.setUserContext
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.implementation.util.JwtService
import java.time.Instant
import java.util.*

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
class UserControllerPostTest {
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
    @DisplayName("POST /api/users/register - Should create user, When user is anonymous")
    fun `test create user as Anonymous`() {
        val requestBody = """
            {
                "name": "test_name4",
                "surname": "test_surname4",
                "email": "test_email4@gmail.com",
                "password": "Dimas@123"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/users/register")
            .contentType("application/json")
            .content(requestBody))
            .andExpect(status().isCreated)
            .andExpectAll(
                jsonPath("$.id").value(4),
                jsonPath("$.name").value("test_name4"),
                jsonPath("$.surname").value("test_surname4"),
                jsonPath("$.email").value("test_email4@gmail.com")
            )
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/register - Should return 403, When user is not anonymous")
    fun `test create user as NotAnonymous`() {
        setUserContext(1)
        val requestBody = """
            {
                "name": "test_name4",
                "surname": "test_surname4",
                "email": "test_email4@gmail.com",
                "password": "Dimas@123"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/users/register")
            .contentType("application/json")
            .content(requestBody))
            .andExpect(status().isForbidden)
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/users/register - Should return 400, When email is not unique")
    fun `test create user not unique email`() {
        val requestBody = """
            {
                "name": "test_name1",
                "surname": "test_surname1",
                "email": "test_email1@gmail.com",
                "password": "Dimas@123"
            }
            """.trimIndent()

        mockMvc.perform(
            post("/api/users/register")
            .contentType("application/json")
            .content(requestBody)
        )
            .andExpectAll(
                status().isBadRequest
            )
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
}