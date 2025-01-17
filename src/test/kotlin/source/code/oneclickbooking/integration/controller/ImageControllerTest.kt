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
import source.code.oneclickbooking.integration.Utils.setAdminContext
import source.code.oneclickbooking.integration.Utils.setUserContext
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.model.EntityType

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/schema/drop-schema.sql",
        "classpath:testcontainers/schema/create-schema.sql"
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

        mockMvc.perform(
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/images/{id} - Should return 404 Not Found")
    fun getNotFound() {
        mockMvc.perform(get("/api/images/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    @SqlSetup
    @DisplayName("GET /api/images/parent/{parentType}/{parentId} - Should return 200 OK")
    fun getAllImagesForParent() {
        setAdminContext(1)

        insertImage(EntityType.REVIEW, 1)
        insertImage(EntityType.REVIEW, 1)

        mockMvc.perform(get("/api/images/parent/REVIEW/1"))
            .andExpectAll(
                status().isOk,
                jsonPath("$.length()").value(2)
            )
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should create and return 200")
    fun create() {
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
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should create and return 200, When user is admin")
    fun createAdmin() {
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
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should return 403 Forbidden, When user is not the author")
    fun createForbidden() {
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
    }

    @Test
    @SqlSetup
    @DisplayName("POST /api/images - Should return 403, When user is not admin")
    fun createForbiddenAdmin() {
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
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 204 No Content")
    fun delete() {
        setUserContext(1)

        insertImage(EntityType.REVIEW, 1)

        mockMvc.perform(delete("/api/images/1"))
            .andExpect(status().isNoContent)
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 404 Not Found")
    fun deleteNotFound() {
        setUserContext(1)

        mockMvc.perform(delete("/api/images/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 403 Forbidden, When user is not the author")
    fun deleteForbidden() {
        insertImage(EntityType.REVIEW, 1)

        setUserContext(1)
        mockMvc.perform(delete("/api/images/1"))
            .andExpect(status().isForbidden)
    }

    @Test
    @SqlSetup
    @DisplayName("DELETE /api/images/{id} - Should return 403 Forbidden, When user is not admin")
    fun deleteForbiddenAdmin() {
        insertImage(EntityType.SERVICE_POINT, 1)

        setUserContext(1)
        LOGGER.info(SecurityContextHolder.getContext().authentication.authorities.toString())

        mockMvc.perform(delete("/api/images/1"))
            .andExpect(status().isForbidden)
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(ImageControllerTest::class.java)
    }
}
