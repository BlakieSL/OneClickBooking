package source.code.oneclickbooking.exception

import org.springframework.security.core.AuthenticationException


class JwtAuthenticationException(
    msg: String
) : AuthenticationException(msg)