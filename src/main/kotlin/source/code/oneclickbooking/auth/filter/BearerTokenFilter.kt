package source.code.oneclickbooking.auth.filter

import jakarta.servlet.http.HttpFilter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import source.code.oneclickbooking.auth.JwtService

class BearerTokenFilter(
    private val jwtService: JwtService
) : HttpFilter() {
    private val failureHandler: AuthenticationFailureHandler = SimpleUrlAuthenticationFailureHandler()


}