package source.code.oneclickbooking.integration.controller.review

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
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
class ReviewControllerGetTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews/{id} should return review")
    @SqlSetup
    fun `test get should return review`() {
        val reviewId = 1

        mockMvc.perform(get("/api/reviews/$reviewId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(reviewId))
            .andExpect(jsonPath("$.text").value("test_text1"))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews/{id} should return 404 when review not found")
    @SqlSetup
    fun `test get should return 404 when review not found`() {
        val reviewId = 100

        mockMvc.perform(get("/api/reviews/$reviewId"))
            .andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews/{id} should return 400 when id not a number")
    fun `test get should return 400 when id not a number`() {
        mockMvc.perform(get("/api/reviews/abc"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews should return all reviews")
    @SqlSetup
    fun `test get all should return all reviews`() {
        mockMvc.perform(get("/api/reviews"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews should return empty list when no reviews")
    fun `test get all should return empty list when no reviews`() {
        mockMvc.perform(get("/api/reviews"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))
    }
}