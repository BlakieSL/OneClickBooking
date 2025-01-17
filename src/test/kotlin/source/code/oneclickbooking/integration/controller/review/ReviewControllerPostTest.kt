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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.Utils.createBookingSql
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
class ReviewControllerPostTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }


    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by employee")
    @SqlSetup
    fun `test get filtered should return filtered reviews by employee`() {
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
            .andExpect(jsonPath("$.reviews.size()").value(1))
            .andExpect(jsonPath("$.reviews[0].text").value("test_text1"))
            .andExpect(jsonPath("$.averageRating").value(5.0))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point`() {
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
            .andExpect(jsonPath("$.reviews.size()").value(2))
            .andExpect(jsonPath("$.averageRating").value(4.5))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point" +
            " and employee")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point and employee`() {
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
            .andExpect(jsonPath("$.reviews.size()").value(1))
            .andExpect(jsonPath("$.reviews[0].text").value("test_text1"))
            .andExpect(jsonPath("$.averageRating").value(5.0));
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point " +
            "and text not null")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point and text not null`() {
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
            .andExpect(jsonPath("$.reviews.size()").value(2))
            .andExpect(jsonPath("$.averageRating").value(4.5))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by user")
    @SqlSetup
    fun `test get filtered should return filtered reviews by user`() {
        val requestBody = """
            {
                "filterCriteria": [
                    { 
                        "filterKey": "USER",
                        "value": 1, 
                        "operation": "EQUAL" 
                    }
                ],
                "dataOption": "AND"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.reviews.size()").value(2))
            .andExpect(jsonPath("$.reviews[0].text").value("test_text2"))
            .andExpect(jsonPath("$.averageRating").value(4.5))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return 400 when filter key is invalid")
    fun `test get filtered should return 400 when filter key is invalid`() {
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return 200 when request body is invalid")
    fun `test get filtered should return 400 when request body is invalid`() {
        val requestBody = """
        {
            "wrongKey": "wrongValue"
        }
        """.trimIndent()

        mockMvc.perform(
            post("/api/reviews/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
    }


    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should create review")
    @SqlSetup
    fun `test create should create review`() {
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should return 403 when booking is not completed")
    @SqlSetup
    fun `test create should return 400 when booking not completed`() {
        val requestBody = """
            {
                "rating": 5,
                "bookingId": 4
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "2021-10-10", 1, 1, 1, 1, "PENDING")

        mockMvc.perform(
            post("/api/reviews")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should return 400 when request is invalid")
    fun `test create should return 400 when request is invalid`() {
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should return 404 when booking not found")
    fun `test create should return 404 when booking not found`() {
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
    }
}