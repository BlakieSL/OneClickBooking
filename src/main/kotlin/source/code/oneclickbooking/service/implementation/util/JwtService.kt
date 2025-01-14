package source.code.oneclickbooking.service.implementation.util

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import source.code.oneclickbooking.exception.InvalidRefreshTokenException
import source.code.oneclickbooking.exception.JwtAuthenticationException
import source.code.oneclickbooking.utils.ExceptionMessages
import java.text.ParseException
import java.time.Instant
import java.util.*

@Service
class JwtService(@Value("\${spring.jws.sharedKey}") sharedKey: String) {
    companion object {
        const val ACCESS_TOKEN_DURATION_MINUTES = 15
        const val REFRESH_TOKEN_DURATION_MINUTES = 60 * 24 * 30
        const val ACCESS_TOKEN_TYPE = "ACCESS"
        const val REFRESH_TOKEN = "REFRESH"
    }
    private val algorithm = JWSAlgorithm.HS256
    private val signer = MACSigner(sharedKey.toByteArray())
    private val verifier = MACVerifier(sharedKey.toByteArray())

    fun createAccessToken(username: String, userId: Int, authorities: List<String>) : String {
        return createSignedJWT(
            username,
            userId,
            authorities,
            ACCESS_TOKEN_DURATION_MINUTES.toLong(),
            ACCESS_TOKEN_TYPE
        )
    }

    fun createRefreshToken(username: String, userId: Int, authorities: List<String>) : String {
        return createSignedJWT(
            username,
            userId,
            authorities,
            REFRESH_TOKEN_DURATION_MINUTES.toLong(),
            REFRESH_TOKEN
        )
    }

    fun refreshAccessToken(refreshToken: String) : String {
        return try {
            val signedJWT = JWTParser.parse(refreshToken) as SignedJWT
            verifySignature(signedJWT)
            verifyExpirationTime(signedJWT)

            val claims = signedJWT.jwtClaimsSet

            val subject = claims.subject
                ?: throw InvalidRefreshTokenException(ExceptionMessages.REFRESH_TOKEN_NO_SUBJECT)

            val userId = claims.getIntegerClaim("userId")
                ?: throw InvalidRefreshTokenException(ExceptionMessages.REFRESH_TOKEN_NO_USER_ID)

            val authorities = claims.getStringListClaim("authorities")
                ?: throw InvalidRefreshTokenException(ExceptionMessages.REFRESH_TOKEN_NO_AUTHORITIES)

            createAccessToken(subject, userId, authorities)
        } catch (e: ParseException) {
            throw InvalidRefreshTokenException(ExceptionMessages.JWT_TOKEN_PARSE_ERROR)
        }
    }

    private fun createSignedJWT(
        username: String,
        userId: Int,
        authorities: List<String>,
        durationMinutes: Long,
        tokenType: String
    ) : String {
        return try {
            val claimSet = buildClaims(username, userId, authorities, durationMinutes, tokenType)
            val signedJWT = SignedJWT(JWSHeader(algorithm), claimSet)
            signedJWT.sign(signer)
            signedJWT.serialize()
        } catch (e: JOSEException) {
            throw JwtAuthenticationException(ExceptionMessages.JWT_SIGNATURE_INVALID)
        }
    }

    fun verifySignature(signedJWT: SignedJWT)  {
        try {
            if(!signedJWT.verify(verifier)) {
                throw JwtAuthenticationException(
                    messageKey = ExceptionMessages.JWT_NOT_VERIFIED,
                    args = arrayOf(signedJWT.serialize())
                )
            }
        } catch (e: JOSEException) {
            throw JwtAuthenticationException(
                messageKey = ExceptionMessages.JWT_NOT_VERIFIED,
                args = arrayOf(signedJWT.serialize())
            )
        }
    }

    fun verifyExpirationTime(signedJWT: SignedJWT) {
        try {
            val expiration = signedJWT.jwtClaimsSet.expirationTime
            if (expiration.before(currentDate())) {
                throw JwtAuthenticationException(ExceptionMessages.JWT_EXPIRED_TOKEN)
            }
        } catch (e: ParseException) {
            throw JwtAuthenticationException(ExceptionMessages.JWT_TOKEN_PARSE_ERROR)
        }
    }

    fun authentication(signedJWT: SignedJWT) : Authentication {
        return try {
            val claims = signedJWT.jwtClaimsSet
            val subject = claims.subject
            val userId = claims.getIntegerClaim("userId")
            val authorities = getAuthorities(signedJWT)
            CustomAuthenticationToken(subject, userId, null, authorities)
        } catch (e: ParseException) {
            throw JwtAuthenticationException(
                messageKey = ExceptionMessages.JWT_TOKEN_PARSE_ERROR,
                args = arrayOf(signedJWT.serialize())
            )
        }
    }

    private fun buildClaims(
        username: String,
        userId: Int,
        authorities: List<String>,
        durationMinutes: Long,
        tokenType: String
    ) : JWTClaimsSet {
        return JWTClaimsSet.Builder()
            .subject(username)
            .issueTime(currentDate())
            .expirationTime(expirationDate(durationMinutes))
            .claim("userId", userId)
            .claim("authorities", authorities)
            .claim("tokenType", tokenType)
            .build()
    }

    private fun currentDate() : Date {
        return Date.from(Instant.now())
    }

    private fun expirationDate(minutesFromNow: Long) : Date {
        return Date.from(Instant.now().plusSeconds(minutesFromNow * 60))
    }

    private fun getAuthorities(signedJWT: SignedJWT) : List<SimpleGrantedAuthority> {
        val claims = signedJWT.jwtClaimsSet
        val roles = claims.getStringListClaim("authorities")
        return roles.map { SimpleGrantedAuthority(it) }
    }

}