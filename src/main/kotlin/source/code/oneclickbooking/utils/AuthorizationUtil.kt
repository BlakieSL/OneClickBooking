package source.code.oneclickbooking.utils

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import java.util.*

object AuthorizationUtil {
    fun isOwnerOrAdmin(ownerId: Int?): Boolean {
        val auth = getAuthContext()
        if (auth is CustomAuthenticationToken) {
            val currentUserId = auth.userId
            val isAdmin = isAdmin(auth)

            return isAdmin || Optional.ofNullable(ownerId)
                .map { it == currentUserId }
                .orElse(false)
        }
        return false
    }

    fun isAdmin(): Boolean {
        val auth = getAuthContext()
        if (auth is CustomAuthenticationToken) {
            return isAdmin(auth)
        }
        return false
    }

    fun getUserId(): Int {
        val auth = getAuthContext()
        if (auth !is CustomAuthenticationToken) {
            throw IllegalStateException("User is not authenticated")
        }
        return auth.userId
    }

    private fun getAuthContext (): Authentication {
        return SecurityContextHolder.getContext().authentication
    }

    private fun isAdmin (authorization: CustomAuthenticationToken): Boolean {
        return authorization.authorities.any { it.authority == "ROLE_ADMIN" }
    }
}