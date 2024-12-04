package source.code.oneclickbooking.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import source.code.oneclickbooking.service.declaration.UserService
import source.code.oneclickbooking.service.implementation.JwtService

class JwtAuthenticationSuccessHandler(
    private val jwtService: JwtService,
    private val userService: UserService
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val authorities = authentication.authorities.map(GrantedAuthority::getAuthority)
        val userId = userService.getUserId(authentication.name)
        val accessToken = jwtService.createAccessToken(authentication.name, userId, authorities)
        val refreshToken = jwtService.createRefreshToken(authentication.name, userId, authorities)
        ObjectMapper().writeValue(response.writer, JwtWrapper(accessToken, refreshToken))
    }

    private data class JwtWrapper(val accessToken: String, val refreshToken: String)
}