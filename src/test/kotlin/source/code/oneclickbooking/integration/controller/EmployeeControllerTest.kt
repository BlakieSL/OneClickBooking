package source.code.oneclickbooking.integration.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.annotation.SqlSetup

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql"
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/employees/{id} should return employee")
    @SqlSetup
    fun `test get should return employee`() {
        mockMvc.perform(get("/api/employees/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("test_username1"))
            .andExpect(jsonPath("$.description").value("test_description1"))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/employees/{id} should return 404 when employee not found")
    fun `test get should return 404 when employee not found`() {
        mockMvc.perform(get("/api/employees/100"))
            .andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/employees/{id} should return 400 when id is not a number")
    fun `test get should return 400 when id is not a number`() {
        mockMvc.perform(get("/api/employees/abc"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/employees should return all employees")
    @SqlSetup
    fun `test get all should return all employees`() {
        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].username").value("test_username1"))
            .andExpect(jsonPath("$[0].description").value("test_description1"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].username").value("test_username2"))
            .andExpect(jsonPath("$[1].description").value("test_description2"))
            .andExpect(jsonPath("$[2].id").value(3))
            .andExpect(jsonPath("$[2].username").value("test_username3"))
            .andExpect(jsonPath("$[2].description").value("test_description3"))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/employees should return empty list when no employees found")
    fun `test get all should return empty list when no employees found`() {
        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/employees/service-point/{servicePointId}/treatment/{treatmentId} "+
            "should return employees")
    @SqlSetup
    fun `test get by service point and treatment should return employees`() {
        mockMvc.perform(get("/api/employees/service-point/1/treatment/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].username").value("test_username1"))
            .andExpect(jsonPath("$[0].description").value("test_description1"))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/employees/service-point/{servicePointId}/treatment/{treatmentId} "+
            "should return empty list when no employees found")
    fun `test get by service point and treatment should return empty list when no employees found`() {
        mockMvc.perform(get("/api/employees/service-point/1/treatment/100"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))
    }
}