package source.code.oneclickbooking.exception

import org.springframework.security.core.AuthenticationException


class JwtAuthenticationException(message: String) : AuthenticationException(message)