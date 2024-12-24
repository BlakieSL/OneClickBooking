package source.code.oneclickbooking.integration.localization

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.i18n.LocaleContextHolder
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.helper.MessageResolver
import source.code.oneclickbooking.model.User
import java.util.*

@SpringBootTest
class LocalizationTest {
    private lateinit var messageResolver: MessageResolver

    @Autowired
    fun setMessageResolver(messageResolver: MessageResolver) {
        this.messageResolver = messageResolver
    }

    @BeforeEach
    fun setUp() {
        LocaleContextHolder.setLocale(Locale.ENGLISH)
    }

    @Test
    fun `should resolve en RecordNotFound`() {
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
            assertEquals("Пользователь не найден для идентификаторов: 999", ex.message)
        }
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(LocalizationTest::class.java)
    }
}