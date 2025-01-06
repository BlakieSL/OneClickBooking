package source.code.oneclickbooking.service.implementation.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class MessageResolver() {
    private lateinit var messageSource: MessageSource

    @Autowired
    fun setMessageSource(messageSource: MessageSource) {
        this.messageSource = messageSource
    }

    fun getMessage(key: String, vararg args: Any): String {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale())
    }
}