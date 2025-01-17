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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
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
class ReviewControllerPatchTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should update review when user is author")
    @SqlSetup
    fun `test update should update review when user is author`() {
        setUserContext(userId = 1)
        val reviewId = 1

        val requestBody = """
            {
                "text": "Updated review content",
                "rating": 4
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/reviews/$reviewId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(reviewId))
            .andExpect(jsonPath("$.text").value("Updated review content"))
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should update when user is admin")
    @SqlSetup
    fun `test update should update when user is admin`() {
        setAdminContext(userId = 3)
        val reviewId = 1

        val requestBody = """
            {
                "text": "Updated review content",
                "rating": 4
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/reviews/$reviewId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(reviewId))
            .andExpect(jsonPath("$.text").value("Updated review content"))
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should return 404 when review not found")
    @SqlSetup
    fun `test update should return 404 when review not found`() {
        setUserContext(userId = 1)
        val reviewId = 100

        val requestBody = """
            {
                "text": "Updated review content",
                "rating": 4
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/reviews/$reviewId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test update should return 403 when user is not author`() {
        setUserContext(userId = 2)
        val reviewId = 1

        val requestBody = """
            {
                "text": "Updated review content",
                "rating": 4
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/reviews/$reviewId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isForbidden)
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should ignore illegal field")
    @SqlSetup
    fun `test update should ignore illegal field`() {
        setUserContext(userId = 1)
        val reviewId = 1

        val requestBody = """
            {
                "text": "Updated review content",
                "illegalField": "illegalValue",
                "rating": 4
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/reviews/$reviewId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(reviewId))
            .andExpect(jsonPath("$.text").value("Updated review content"))
            .andExpect(jsonPath("$.rating").value(4))

    }
}