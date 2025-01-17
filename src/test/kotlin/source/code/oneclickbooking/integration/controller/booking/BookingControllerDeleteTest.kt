package source.code.oneclickbooking.integration.controller.booking

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.TestConfiguration
import source.code.oneclickbooking.integration.Utils
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
class BookingControllerDeleteTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }


    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should delete booking, When user is admin")
    @SqlSetup
    fun `test delete should delete booking when user is admin`() {
        Utils.setAdminContext(userId = 3)
        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should return 403, When user is not admin")
    @SqlSetup
    fun `test delete should return 403 when user is not admin`() {
        Utils.setUserContext(userId = 2)
        val bookingId = 1

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isForbidden)
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should return 404, When booking not found")
    @SqlSetup
    fun `test delete should return 404 when booking not found`() {
        Utils.setAdminContext(userId = 3)
        val bookingId = 100

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)
    }
}