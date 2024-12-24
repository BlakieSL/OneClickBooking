package source.code.oneclickbooking.config

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import source.code.oneclickbooking.helper.MessageResolver

@Configuration
class MessageResolverConfig(
    private val messageResolver: MessageResolver
) {
    @PostConstruct
    fun initialize() {
        MessageResolverHolder.messageResolver = messageResolver
    }
}

object MessageResolverHolder {
    lateinit var messageResolver: MessageResolver
}