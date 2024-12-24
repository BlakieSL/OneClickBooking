package source.code.oneclickbooking.helper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
object MessageResolver {
    private lateinit var messageSource: MessageSource

    @Autowired
    fun setMessageSource(messageSource: MessageSource) {
        this.messageSource = messageSource
    }

    fun getMessage(key: String, vararg args: Any): String {
        return getMessage(key, *args, locale = LocaleContextHolder.getLocale())
    }

    private fun getMessage(key: String, vararg args: Any, locale: Locale): String {
        return messageSource.getMessage(key, args, locale)
    }
}