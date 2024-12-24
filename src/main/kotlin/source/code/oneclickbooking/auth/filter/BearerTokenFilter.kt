package source.code.oneclickbooking.auth.filter

import com.nimbusds.jwt.SignedJWT
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpFilter
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import source.code.oneclickbooking.service.implementation.util.JwtService
import source.code.oneclickbooking.exception.JwtAuthenticationException
import source.code.oneclickbooking.helper.ExceptionMessages
import java.text.ParseException

class BearerTokenFilter(
    private val jwtService: JwtService
) : HttpFilter() {
    private val failureHandler: AuthenticationFailureHandler = SimpleUrlAuthenticationFailureHandler()

    override fun doFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val bearerToken = getBearerToken(request)

        if (bearerToken.isNullOrEmpty()) {
            chain.doFilter(request, response)
            return
        }

        try {
            val signedJWT = SignedJWT.parse(bearerToken)
            validateJwt(signedJWT)
            setSecurityContext(signedJWT)
            chain.doFilter(request, response)
        } catch (e: Exception) {
            when (e) {
                is JwtAuthenticationException, is ParseException ->
                    failureHandler.onAuthenticationFailure(
                        request,
                        response,
                        JwtAuthenticationException(ExceptionMessages.JWT_INVALID_TOKEN)
                    )
                else -> throw e
            }
        }
    }

    private fun getBearerToken(request: HttpServletRequest) : String? {
        val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER)
        return if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            authorizationHeader.substring(BEARER_TOKEN_PREFIX.length)
        } else {
            null
        }
    }

    private fun validateJwt(signedJWT: SignedJWT) {
        jwtService.verifySignature(signedJWT)
        jwtService.verifyExpirationTime(signedJWT)
        validateTokenType(signedJWT)
    }

    private fun validateTokenType(signedJWT: SignedJWT) {
        val tokenType = signedJWT.jwtClaimsSet.getStringClaim("tokenType")
        if (ACCESS_TOKEN_TYPE != tokenType) {
            throw JwtAuthenticationException(ExceptionMessages.JWT_INVALID_TOKEN_TYPE)
        }
    }

    private fun setSecurityContext(signedJWT: SignedJWT) {
        val authentication = jwtService.authentication(signedJWT)
        SecurityContextHolder.getContextHolderStrategy().context.authentication = authentication
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_TOKEN_PREFIX = "Bearer "
        private const val ACCESS_TOKEN_TYPE = "ACCESS"
    }
}