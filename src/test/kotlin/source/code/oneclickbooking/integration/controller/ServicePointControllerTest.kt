package source.code.oneclickbooking.integration.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.annotation.SqlSetup

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
class ServicePointControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/service-points/{id} - Should return service point")
    @SqlSetup
    fun `test get service point`() {
        logRunning()

        mockMvc.perform(get("/api/service-points/1"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(1),
                jsonPath("$.email").value("test_email1@gmail.com"),
                jsonPath("$.location").value("test_location1"),
                jsonPath("$.name").value("test_name1"),
                jsonPath("$.phone").value("test_phone1")
            )

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/service-points/{id} - Should return 404, When not found")
    fun `test get service point not found`() {
        logRunning()

        mockMvc.perform(get("/api/service-points/999")).andExpect(status().isNotFound)

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @SqlSetup
    @DisplayName("GET /api/service-points - Should return all service points")
    fun `test get all service points`() {
        logRunning()

        mockMvc.perform(get("/api/service-points"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(2),
                jsonPath("$[0].id").value(1),
                jsonPath("$[1].id").value(2)
            )

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/service-points - Should return empty list")
    fun `test get all service points empty`() {
        logRunning()

        mockMvc.perform(get("/api/service-points"))
            .andExpect(status().isOk)
            .andExpectAll(jsonPath("$.size()").value(0))

        logPassed()
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