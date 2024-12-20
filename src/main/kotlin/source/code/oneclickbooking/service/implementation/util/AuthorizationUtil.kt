package source.code.oneclickbooking.service.implementation.util

import org.springframework.security.core.context.SecurityContextHolder
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import java.util.*

class AuthorizationUtil {
    companion object {
        fun isOwnerOrAdmin(ownerId: Int?): Boolean {
            val auth = SecurityContextHolder.getContext().authentication
            if (auth is CustomAuthenticationToken) {
                val currentUserId = auth.userId
                val isAdmin = auth.authorities.any { it.authority == "ROLE_ADMIN" }

                return isAdmin || Optional.ofNullable(ownerId)
                    .map { it == currentUserId }
                    .orElse(false)
            }
            return false
        }

        fun getUserId(): Int {
            val auth = SecurityContextHolder.getContext().authentication as CustomAuthenticationToken
            return auth.userId
        }
    }
}