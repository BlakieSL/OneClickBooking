package source.code.oneclickbooking.exception

import source.code.oneclickbooking.config.MessageResolverHolder

open class LocalizedException(
    private val messageKey: String,
    private vararg val args: Any,
    cause: Throwable? = null
) : RuntimeException(null, cause) {
    override val message: String
        get() = MessageResolverHolder.messageResolver.getMessage(messageKey, *args)
}