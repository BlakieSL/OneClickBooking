package source.code.oneclickbooking.integration.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import source.code.oneclickbooking.integration.annotation.SqlSetup

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql",
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerTest {
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
        LOGGER.info("RUNNING test get review by id should return review...")

        val reviewId = 1

        mockMvc.perform(get("/api/reviews/$reviewId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(reviewId))
            .andExpect(jsonPath("$.text").value("test_text1"))

        LOGGER.info("test get review by id should return review PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews/{id} should return 404 when review not found")
    @SqlSetup
    fun `test get should return 404 when review not found`() {
        LOGGER.info("RUNNING test get review by id should return 404 when review not found...")

        val reviewId = 100

        mockMvc.perform(get("/api/reviews/$reviewId"))
            .andExpect(status().isNotFound)

        LOGGER.info("test get review by id should return 404 when review not found PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews/{id} should return 400 when id not a number")
    fun `test get should return 400 when id not a number`() {
        LOGGER.info("RUNNING test get review by id should return 400 when id not a number...")

        mockMvc.perform(get("/api/reviews/abc"))
            .andExpect(status().isBadRequest)

        LOGGER.info("test get review by id should return 400 when id not a number PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews should return all reviews")
    @SqlSetup
    fun `test get all should return all reviews`() {
        LOGGER.info("RUNNING test get all reviews should return all reviews...")

        mockMvc.perform(get("/api/reviews"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))

        LOGGER.info("test get all reviews should return all reviews PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews should return empty list when no reviews")
    fun `test get all should return empty list when no reviews`() {
        LOGGER.info("RUNNING test get all reviews should return empty list when no reviews...")

        mockMvc.perform(get("/api/reviews"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))

        LOGGER.info("test get all reviews should return empty list when no reviews PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by employee")
    @SqlSetup
    fun `test get filtered should return filtered reviews by employee`() {
        LOGGER.info("RUNNING test get filtered reviews by employee...")

        val requestBody = """
        {
            "filterCriteria": [
                { "filterKey": "EMPLOYEE", "value": 1, "operation": "EQUAL" }
            ],
            "dataOption": "AND"
        }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].text").value("test_text1"))

        LOGGER.info("test get filtered reviews by employee PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point`() {
        LOGGER.info("RUNNING test get filtered reviews by service point...")

        val requestBody = """
        {
            "filterCriteria": [
                { "filterKey": "SERVICE_POINT", "value": 1, "operation": "EQUAL" }
            ],
            "dataOption": "AND"
        }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))

        LOGGER.info("test get filtered reviews by service point PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point" +
            " and employee")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point and employee`() {
        LOGGER.info("RUNNING test get filtered reviews by service point and employee...")

        val requestBody = """
        {
            "filterCriteria": [
                { "filterKey": "SERVICE_POINT", "value": 1, "operation": "EQUAL" },
                { "filterKey": "EMPLOYEE", "value": 1, "operation": "EQUAL" }
            ],
            "dataOption": "AND"
        }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].text").value("test_text1"))

        LOGGER.info("test get filtered reviews by service point and employee PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point " +
            "and text not null")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point and text not null`() {
        LOGGER.info("RUNNING test get filtered reviews by service point and text not null...")

        val requestBody = """
        {
            "filterCriteria": [
               { "filterKey": "SERVICE_POINT", "value": 1, "operation": "EQUAL" },
               { "filterKey": "TEXT", "value": "NOT_NULL", "operation": "EQUAL" }
            ],
            "dataOption": "AND"
        }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))

        LOGGER.info("test get filtered reviews by service point and text not null PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return 400 when filter key is invalid")
    fun `test get filtered should return 400 when filter key is invalid`() {
        LOGGER.info("RUNNING test get filtered reviews should return 400 when filter key is invalid...")

        val requestBody = """
        {
            "filterCriteria": [
                { "filterKey": "INVALID_KEY", "value": "invalidValue", "operation": "EQUAL" }
            ],
            "dataOption": "AND"
        }
    """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        LOGGER.info("test get filtered reviews should return 400 when filter key is invalid PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return 400 when request body is invalid")
    fun `test get filtered should return 400 when request body is invalid`() {
        LOGGER.info("RUNNING test get filtered reviews should return 400 when request body is invalid...")

        val requestBody = """
        {
            "wrongKey": "wrongValue"
        }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        LOGGER.info("test get filtered reviews should return 400 when request body is invalid PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should create review")
    @SqlSetup
    fun `test create should create review`() {
        LOGGER.info("RUNNING test create review should create review...")

        val requestBody = """
            {
                "rating": 5,
                "text": "Excellent service!",
                "bookingId": 3
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.text").value("Excellent service!"))

        LOGGER.info("test create review should create review PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should return 400 when request is invalid")
    fun `test create should return 400 when request is invalid`() {
        LOGGER.info("RUNNING test create review should return 400 when request is invalid...")

        val requestBody = """
            {
                "rating": 5
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        LOGGER.info("test create review should return 400 when request is invalid PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should return 404 when booking not found")
    fun `test create should return 404 when booking not found`() {
        LOGGER.info("RUNNING test create review should return 404 when booking not found...")

        val requestBody = """
            {
                "rating": 5,
                "text": "Excellent service!",
                "bookingId": 100
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isNotFound)

        LOGGER.info("test create review should return 404 when booking not found PASSED!")
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should delete review when user is author")
    @SqlSetup
    fun `test delete should delete review when user is author`() {
        LOGGER.info("RUNNING test delete review should delete review when user is author...")

        setUserContext(userId = 1)
        val reviewId = 1

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isNoContent)

        LOGGER.info("test delete review should delete review when user is author PASSED!")
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should return 404 when review not found")
    @SqlSetup
    fun `test delete should return 404 when review not found`() {
        LOGGER.info("RUNNING test delete review should return 404 when review not found...")

        setUserContext(userId = 1)
        val reviewId = 100

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isNotFound)

        LOGGER.info("test delete review should return 404 when review not found PASSED!")
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test delete should return 403 when user is not author`() {
        LOGGER.info("RUNNING test delete review should return 403 when user is not author...")

        setUserContext(userId = 2)
        val reviewId = 1

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isForbidden)

        LOGGER.info("test delete review should return 403 when user is not author PASSED!")
    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should delete review when user is admin")
    @SqlSetup
    fun `test delete should delete review when user is admin`() {
        LOGGER.info("RUNNING test delete review should delete review when user is admin...")

        setAdminContext(userId = 3)
        val reviewId = 1

        mockMvc.perform(delete("/api/reviews/$reviewId"))
            .andExpect(status().isNoContent)

        LOGGER.info("test delete review should delete review when user is admin PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should update review when user is author")
    @SqlSetup
    fun `test update should update review when user is author`() {
        LOGGER.info("RUNNING test update review should update review when user is author...")

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

        LOGGER.info("test update review should update review when user is author PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should update when user is admin")
    @SqlSetup
    fun `test update should update when user is admin`() {
        LOGGER.info("RUNNING test update review should update when user is admin...")

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

        LOGGER.info("test update review should update when user is admin PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should return 404 when review not found")
    @SqlSetup
    fun `test update should return 404 when review not found`() {
        LOGGER.info("RUNNING test update review should return 404 when review not found...")

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

        LOGGER.info("test update review should return 404 when review not found PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test update should return 403 when user is not author`() {
        LOGGER.info("RUNNING test update review should return 403 when user is not author...")

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

        LOGGER.info("test update review should return 403 when user is not author PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/reviews/{id} should ignore illegal field")
    @SqlSetup
    fun `test update should ignore illegal field`() {
        LOGGER.info("RUNNING test update review should ignore illegal field...")

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

        LOGGER.info("test update review should ignore illegal field PASSED!")
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

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ReviewControllerTest::class.java)
    }
}