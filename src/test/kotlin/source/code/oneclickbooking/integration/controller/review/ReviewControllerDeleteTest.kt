package source.code.oneclickbooking.integration.controller.review

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.Utils.setAdminContext
import source.code.oneclickbooking.integration.Utils.setUserContext
import source.code.oneclickbooking.integration.annotation.SqlSetup

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/schema/drop-schema.sql",
        "classpath:testcontainers/schema/create-schema.sql",
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerDeleteTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should delete review when user is author")
    @SqlSetup
    fun `test delete should delete review when user is author`() {
        setUserContext(userId = 1)
        val reviewId = 1

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isNoContent)
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should return 404 when review not found")
    @SqlSetup
    fun `test delete should return 404 when review not found`() {
        setUserContext(userId = 1)
        val reviewId = 100

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test delete should return 403 when user is not author`() {
        setUserContext(userId = 2)
        val reviewId = 1

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isForbidden)
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should delete review when user is admin")
    @SqlSetup
    fun `test delete should delete review when user is admin`() {
        setAdminContext(userId = 3)
        val reviewId = 1

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isNoContent)
    }
}