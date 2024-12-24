package source.code.oneclickbooking.integration.localization

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.User
import java.util.*

@ActiveProfiles("test")
@Testcontainers
@Sql(
    value = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql",
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class LocalizationTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    fun `should resolve en RecordNotFound`() {
        LOGGER.info(LocaleContextHolder.getLocale().toLanguageTag())
        LocaleContextHolder.setLocale(Locale.ENGLISH)
        LOGGER.info(LocaleContextHolder.getLocale().toLanguageTag())

        try {
            throw RecordNotFoundException(entityClass = User::class, identifiers = arrayOf(999))
        } catch (ex: RecordNotFoundException) {
            assertEquals("User not found for identifiers: 999", ex.message)
        }
    }

    @Test
    fun `should resolve ru RecordNotFound`() {
        LOGGER.info(LocaleContextHolder.getLocale().toLanguageTag())
        LocaleContextHolder.setLocale(Locale.of("ru"))
        LOGGER.info(LocaleContextHolder.getLocale().toLanguageTag())

        try {
            throw RecordNotFoundException(entityClass = User::class, identifiers = arrayOf(999))
        } catch (ex: RecordNotFoundException) {
            assertEquals("User не найден для идентификаторов: 999", ex.message)
        }
    }

    @Test
    fun `should resolve pl RecordNotFound`() {
        LOGGER.info(LocaleContextHolder.getLocale().toLanguageTag())
        LocaleContextHolder.setLocale(Locale.forLanguageTag("pl"))
        LOGGER.info(LocaleContextHolder.getLocale().toLanguageTag())

        try {
            throw RecordNotFoundException(entityClass = User::class, identifiers = arrayOf(999))
        } catch (ex: RecordNotFoundException) {
            assertEquals("User nie znaleziono dla identyfikatorów: 999", ex.message)
        }
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/service-points/{id} - Should return 404, When not found, With en")
    fun `should return 404 en message`() {
        mockMvc.perform(
            get("/api/service-points/999")
                .header("Accept-Language", "en")
        ).andExpect(status().isNotFound)
            .andExpect(content().string("ServicePoint not found for identifiers: 999"))
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/service-points/{id} - Should return 404, When not found, With ru")
    fun `should return 404 ru message`() {
        mockMvc.perform(
            get("/api/service-points/999")
                .header("Accept-Language", "ru")
        ).andExpect(status().isNotFound)
            .andExpect(content().string("ServicePoint не найден для идентификаторов: 999"))
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("GET /api/service-points/{id} - Should return 404, When not found, With pl")
    fun `should return 404 pl message`() {
        mockMvc.perform(
            get("/api/service-points/999")
                .header("Accept-Language", "pl")
        ).andExpect(status().isNotFound)
            .andExpect(content().string("ServicePoint nie znaleziono dla identyfikatorów: 999"))
    }



    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(LocalizationTest::class.java)
    }
}