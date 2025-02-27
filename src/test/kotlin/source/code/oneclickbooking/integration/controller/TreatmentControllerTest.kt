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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.integration.controller.booking.BookingControllerDeleteTest

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
class TreatmentControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @SqlSetup
    @DisplayName("GET /api/treatments/{id} - Should return treatment")
    fun `test get treatment`() {
        mockMvc.perform(get("/api/treatments/1"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(1),
                jsonPath("$.name").value("test_name1"),
                jsonPath("$.description").value("test_description1"),
                jsonPath("$.duration").value(1),
                jsonPath("$.price").value(1.0)
            )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/treatments/{id} - Should return 404, When not found")
    fun `test get treatment not found`() {
        mockMvc.perform(get("/api/treatments/999")).andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @SqlSetup
    @DisplayName("GET /api/treatments - Should return all treatments")
    fun `test get all treatments`() {
        mockMvc.perform(get("/api/treatments"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(3),
                jsonPath("$[0].id").value(1),
                jsonPath("$[1].id").value(2),
                jsonPath("$[2].id").value(3)
            )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/treatments - Should return empty list")
    fun `test get all treatments empty`() {
        mockMvc.perform(get("/api/treatments"))
            .andExpect(status().isOk)
            .andExpectAll(jsonPath("$.size()").value(0))
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @SqlSetup
    @DisplayName("GET /api/treatments/service-point/{servicePointId} - Should return all treatments " +
            "by service point")
    fun `test get all treatments by service point`() {
        mockMvc.perform(get("/api/treatments/service-point/1"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(2),
                jsonPath("$[0].id").value(1),
                jsonPath("$[1].id").value(2)
            )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/treatments/service-point/{servicePointId} - Should return empty list")
    fun `test get all treatments by service point empty`() {
        mockMvc.perform(get("/api/treatments/service-point/999"))
            .andExpect(status().isOk)
            .andExpectAll(jsonPath("$.size()").value(0))
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BookingControllerDeleteTest::class.java)
    }
}

