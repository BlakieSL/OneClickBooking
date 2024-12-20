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
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import source.code.oneclickbooking.integration.annotation.SqlSetup

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql"],
)
@AutoConfigureMockMvc
@SpringBootTest
class ReviewControllerTest {
    private lateinit var mockMode: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMode = mockMvc
    }

     @Test
     @WithMockUser(username = "testuser", roles = ["USER"])
     @DisplayName("Test GET /api/reviews/{id} should return review")
     @SqlSetup
     fun `test get should return review`() {

     }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews/{id} should return 404 when booking not found")
    @SqlSetup
    fun `test get should return 404 when review not found`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews/{id} should return 400 when id not a number")
    fun `test get should return 400 when id not a number`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews should return all reviews")
    @SqlSetup
    fun `test get all should return all reviews`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/reviews should return empty list when no reviews")
    fun `test get all should return empty list when no reviews`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by employee")
    @SqlSetup
    fun `test get filtered should return filtered reviews by employee`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return filtered reviews by service point" +
            " and employee")
    @SqlSetup
    fun `test get filtered should return filtered reviews by service point and employee`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews/filtered should return 400 when filter key is invalid")
    fun `test get filtered should return 400 when filter key is invalid`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should create review")
    @SqlSetup
    fun `test create should create review`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should return 400 when request is invalid")
    fun `test create should return 400 when request is invalid`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/reviews should return 404 when booking not found")
    fun `test create should return 404 when booking not found`() {

    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should delete review when user is author")
    @SqlSetup
    fun `test delete should delete review when user is author`() {

    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should return 404 when review not found")
    @SqlSetup
    fun `test delete should return 404 when review not found`() {

    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test delete should return 403 when user is not author`() {

    }

    @Test
    @DisplayName("Test DELETE /api/reviews/{id} should delete review when user is admin")
    fun `test delete should delete review when user is admin`() {

    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should update review when user is author")
    @SqlSetup
    fun `test update should update review when user is author`() {

    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should update when user is admin")
    @SqlSetup
    fun `test update should update when user is admin`() {

    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should return 404 when review not found")
    @SqlSetup
    fun `test update should return 404 when review not found`() {

    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test update should return 403 when user is not author`() {

    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should return 400 when patch is invalid")
    @SqlSetup
    fun `test update should return 400 when patch is invalid`() {

    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should ingnore illegal field")
    @SqlSetup
    fun `test update should ingnore illegal field`() {

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
        private val LOGGER: Logger = LoggerFactory.getLogger(BookingControllerTest::class.java)
    }
}