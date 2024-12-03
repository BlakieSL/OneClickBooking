package source.code.oneclickbooking.auth

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class CustomAuthenticationToken(
    principal: Any,
    val userId: Int,
    credentials: Any?,
    authorities: Collection<GrantedAuthority>
) : UsernamePasswordAuthenticationToken(principal, credentials, authorities)