package source.code.oneclickbooking.exception

import org.springframework.security.core.AuthenticationException
import source.code.oneclickbooking.helper.MessageResolver


class JwtAuthenticationException(
    messageKey: String,
    vararg args: Any,
) : AuthenticationException(MessageResolver.getMessage(key = messageKey, args = args),)