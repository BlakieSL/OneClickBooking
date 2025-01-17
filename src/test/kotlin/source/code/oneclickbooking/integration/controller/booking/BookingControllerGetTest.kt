package source.code.oneclickbooking.integration.controller.booking

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.TestConfiguration
import source.code.oneclickbooking.integration.annotation.SqlSetup

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/schema/drop-schema.sql",
        "classpath:testcontainers/schema/create-schema.sql"
    ]
)
@Import(TestConfiguration::class)
@AutoConfigureMockMvc
@SpringBootTest
class BookingControllerGetTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/{id} - Should return booking")
    @SqlSetup
    fun `test get should return booking`() {
        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(bookingId),
                jsonPath("$.date").value("2022-01-01T00:00:00"),
                jsonPath("$.userId").value(1),
                jsonPath("$.servicePointId").value(1),
                jsonPath("$.employeeId").value(1),
                jsonPath("$.treatmentId").value(1)
            )
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/{id} - Should return 404 when booking not found")
    @SqlSetup
    fun `test get should return 404 when booking not found`() {
        val bookingId = 100

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/{id} - Should return 400 when id not a number")
    fun `test get should return 400 when id not a number`() {
        mockMvc.perform(get("/api/bookings/abc"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings - Should return all bookings")
    @SqlSetup
    fun `test get all should return all bookings`() {
        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(3),
                jsonPath("$[0].id").value(1),
                jsonPath("$[0].date").value("2022-01-01T00:00:00"),
                jsonPath("$[0].userId").value(1),
                jsonPath("$[0].servicePointId").value(1),
                jsonPath("$[0].employeeId").value(1),
                jsonPath("$[0].treatmentId").value(1)
            )
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings - Should return empty list, When no bookings")
    fun `test get all should return empty list when no bookings`() {
        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))
    }
}