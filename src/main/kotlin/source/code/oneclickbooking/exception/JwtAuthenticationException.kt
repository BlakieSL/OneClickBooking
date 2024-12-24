package source.code.oneclickbooking.exception

import org.springframework.security.core.AuthenticationException
import source.code.oneclickbooking.config.MessageResolverHolder


class JwtAuthenticationException(messageKey: String, vararg args: Any) : AuthenticationException(
    MessageResolverHolder.messageResolver.getMessage(messageKey, *args)
)