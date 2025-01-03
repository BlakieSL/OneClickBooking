package source.code.oneclickbooking.integration.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.model.EntityType

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
class ImageControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    private fun insertImage(parentType: EntityType, parentId: Int) {
        val mockImage = MockMultipartFile(
            "image",
            "test-image.png",
            "image/png",
            "test-image-content".toByteArray()
        )

        val adminAuth = CustomAuthenticationToken(
            principal = "admin",
            userId = 1,
            credentials = null,
            authorities = listOf(SimpleGrantedAuthority("ROLE_ADMIN"))
        )

        val response = mockMvc.perform(
            multipart("/api/images")
                .file(mockImage)
                .param("parentType", "$parentType")
                .param("parentId", "$parentId")
                .with(authentication(adminAuth))
        ).andExpect(status().isOk)
    }

    @Test
    @SqlSetup
    @DisplayName("GET /api/images/{id} - Should return 200 OK")
    fun get() {
        logRunning()
        setAdminContext(1)

        insertImage(EntityType.REVIEW, 1)

        mockMvc.perform(get("/api/images/1"))
            .andExpectAll(
                status().isOk,
                jsonPath("$.id").value(1),
                jsonPath("$.parentType").value("REVIEW"),
                jsonPath("$.parentId").value(1),
                jsonPath("$.image").exists()
            )

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/images/{id} - Should return 404 Not Found")
    fun getNotFound() {
        logRunning()

        mockMvc.perform(get("/api/images/999"))
            .andExpect(status().isNotFound)

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("GET /api/images/parent/{parentType}/{parentId} - Should return 200 OK")
    fun getAllImagesForParent() {
        logRunning()
        setAdminContext(1)

        insertImage(EntityType.REVIEW, 1)
        insertImage(EntityType.REVIEW, 1)

        mockMvc.perform(get("/api/images/parent/REVIEW/1"))
            .andExpectAll(
                status().isOk,
                jsonPath("$.length()").value(2)
            )

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should create and return 200")
    fun create() {
        logRunning()
        setUserContext(1)

        val mockImage = MockMultipartFile(
            "image",
            "test-image.png",
            "image/png",
            "test-image-content".toByteArray()
        )

        mockMvc.perform(
            multipart("/api/images")
                .file(mockImage)
                .param("parentType", "REVIEW")
                .param("parentId", "2")
        ).andExpectAll(
            status().isOk,
            jsonPath("$.id").exists(),
            jsonPath("$.parentType").value("REVIEW"),
            jsonPath("$.parentId").value(2),
            jsonPath("$.image").exists()
        )

        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should create and return 200, When user is admin")
    fun createAdmin() {
        logRunning()
        setAdminContext(1)

        val mockImage = MockMultipartFile(
            "image",
            "test-image.png",
            "image/png",
            "test-image-content".toByteArray()
        )

        mockMvc.perform(
            multipart("/api/images")
                .file(mockImage)
                .param("parentType", "SERVICE_POINT")
                .param("parentId", "1")
        ).andExpectAll(
            status().isOk,
            jsonPath("$.id").exists(),
            jsonPath("$.parentType").value("SERVICE_POINT"),
            jsonPath("$.parentId").value(1),
            jsonPath("$.image").exists()
        )
        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should return 403 Forbidden, When user is not the author")
    fun createForbidden() {
        logRunning()
        setUserContext(2)

        val mockImage = MockMultipartFile(
            "image",
            "test-image.png",
            "image/png",
            "test-image-content".toByteArray()
        )

        mockMvc.perform(
            multipart("/api/images")
                .file(mockImage)
                .param("parentType", "REVIEW")
                .param("parentId", "1")
        ).andExpect(status().isForbidden)
        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should return 403, When user is not admin")
    fun createForbiddenAdmin() {
        logRunning()
        setUserContext(1)

        val mockImage = MockMultipartFile(
            "image",
            "test-image.png",
            "image/png",
            "test-image-content".toByteArray()
        )

        mockMvc.perform(
            multipart("/api/images")
                .file(mockImage)
                .param("parentType", "SERVICE_POINT")
                .param("parentId", "1")
        ).andExpect(status().isForbidden)
        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 204 No Content")
    fun delete() {
        logRunning()
        setUserContext(1)

        insertImage(EntityType.REVIEW, 1)

        mockMvc.perform(delete("/api/images/1"))
            .andExpect(status().isNoContent)
        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 404 Not Found")
    fun deleteNotFound() {
        logRunning()
        setUserContext(1)

        mockMvc.perform(delete("/api/images/999"))
            .andExpect(status().isNotFound)
        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 403 Forbidden, When user is not the author")
    fun deleteForbidden() {
        logRunning()

        insertImage(EntityType.REVIEW, 1)

        setUserContext(1)
        mockMvc.perform(delete("/api/images/1"))
            .andExpect(status().isForbidden)
        logPassed()
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 403 Forbidden, When user is not admin")
    fun deleteForbiddenAdmin() {
        logRunning()

        insertImage(EntityType.SERVICE_POINT, 1)

        setUserContext(1)
        LOGGER.info(SecurityContextHolder.getContext().authentication.authorities.toString())

        mockMvc.perform(delete("/api/images/1"))
            .andExpect(status().isForbidden)
        logPassed()
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

    private fun logRunning() {
        LOGGER.info("RUNNING...")
    }

    private fun logPassed() {
        LOGGER.info("PASSED!")
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ImageControllerTest::class.java)
    }
}
