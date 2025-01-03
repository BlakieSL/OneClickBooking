package source.code.oneclickbooking.auth.filter

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import source.code.oneclickbooking.auth.JwtAuthenticationSuccessHandler
import source.code.oneclickbooking.service.declaration.UserService
import source.code.oneclickbooking.service.implementation.util.JwtService

class JwtAuthenticationFilter(
    private val authenticationManager: AuthenticationManager,
    jwtService: JwtService,
    userService: UserService
) : HttpFilter() {
    private val defaultRequestMatcher: RequestMatcher =
        AntPathRequestMatcher("/api/users/login", "POST")

    private val failureHandler: AuthenticationFailureHandler =
        SimpleUrlAuthenticationFailureHandler()

    private val successHandler: AuthenticationSuccessHandler =
        JwtAuthenticationSuccessHandler(jwtService, userService)

    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        if (!defaultRequestMatcher.matches(request)) {
           chain.doFilter(request, response)
           return
        }

        try {
            val jwtAuthentication = ObjectMapper().readValue(
                request.inputStream,
                JwtAuthenticationToken::class.java
            )

            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                jwtAuthentication.username,
                jwtAuthentication.password
            )

            val authenticationResult: Authentication = authenticationManager
                .authenticate(usernamePasswordAuthenticationToken)

            successHandler.onAuthenticationSuccess(request, response, authenticationResult)
        } catch (e: AuthenticationException) {
            failureHandler.onAuthenticationFailure(request, response, e)
        }
    }

    private data class JwtAuthenticationToken (
        @JsonProperty("username") val username: String,
        @JsonProperty("password") val password: String
    )
}