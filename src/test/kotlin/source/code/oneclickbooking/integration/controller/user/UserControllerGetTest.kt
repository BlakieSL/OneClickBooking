package source.code.oneclickbooking.integration.controller.user

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
class UserControllerGetTest {
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
        setUserContext(1)
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(1),
                jsonPath("$.name").value("test_name1"),
                jsonPath("$.surname").value("test_surname1"),
                jsonPath("$.email").value("test_email1@gmail.com")
            )
    }

    @Test
    @SqlSetup
    @DisplayName("GET /api/users/{id} - Should return user, When user is Admin")
    fun `test get user as Admin`() {
        setAdminContext(1)
        mockMvc.perform(get("/api/users/1"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(1),
                jsonPath("$.name").value("test_name1"),
                jsonPath("$.surname").value("test_surname1"),
                jsonPath("$.email").value("test_email1@gmail.com")
            )
    }

    @Test
    @SqlSetup
    @DisplayName("GET /api/users/{id} - Should return 403, When user is not AccountOwner or Admin")
    fun `test get user as NotAccountOwnerOrAdmin`() {
        setUserContext(2)
        mockMvc.perform(get("/api/users/1")).andExpect(status().isForbidden)

    }
}