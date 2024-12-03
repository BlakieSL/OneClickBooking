package source.code.oneclickbooking.exception

import org.apache.tomcat.websocket.AuthenticationException

class JwtAuthenticationException(
    msg: String
) : AuthenticationException(msg)