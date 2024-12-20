package source.code.oneclickbooking.service.implementation.util

import com.nimbusds.jose.*
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
import source.code.oneclickbooking.exception.JwtAuthenticationException
import java.text.ParseException
import java.time.Instant
import java.util.Date

@Service
class JwtService(@Value("\${spring.jws.sharedKey}") sharedKey: String) {
    companion object {
        private const val ACCESS_TOKEN_DURATION_MINUTES = 15
        private const val REFRESH_TOKEN_DURATION_MINUTES = 60 * 24 * 30
        private const val ACCESS_TOKEN_TYPE = "ACCESS"
        private const val REFRESH_TOKEN = "REFRESH"
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
            listOf("ROLE_REFRESH"),
            REFRESH_TOKEN_DURATION_MINUTES.toLong(),
            REFRESH_TOKEN
        )
    }

    fun refreshAccessToken(refreshToken: String) : String {
        return try {
            val signedJWT = JWTParser.parse(refreshToken) as SignedJWT
            verifySignature(signedJWT)
            verifyExpirationTime(signedJWT)
            createAccessToken(
                signedJWT.jwtClaimsSet.subject,
                signedJWT.jwtClaimsSet.getIntegerClaim("userId"),
                signedJWT.jwtClaimsSet.getStringListClaim("authorities")
            )
        } catch (e: ParseException) {
            throw RuntimeException("Invalid refresh token")
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
            throw JwtAuthenticationException("Failed to sign JWT $e")
        }
    }

    fun verifySignature(signedJWT: SignedJWT)  {
        try {
            if(!signedJWT.verify(verifier)) {
                throw JwtAuthenticationException("JWT not verified - token: ${signedJWT.serialize()}")
            }
        } catch (e: JOSEException) {
            throw JwtAuthenticationException("JWT not verified - token: ${signedJWT.serialize()}")
        }
    }

    fun verifyExpirationTime(signedJWT: SignedJWT) {
        try {
            val expiration = signedJWT.jwtClaimsSet.expirationTime
            if (expiration.before(currentDate())) {
                throw JwtAuthenticationException("JWT expired")
            }
        } catch (e: ParseException) {
            throw JwtAuthenticationException("JWT expiration time is invalid")
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
            throw RuntimeException("Invalid token")
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