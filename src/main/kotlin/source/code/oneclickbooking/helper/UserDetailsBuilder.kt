package source.code.oneclickbooking.helper

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import source.code.oneclickbooking.dto.other.UserCredentialsDto

object UserDetailsBuilder {
    fun build(dto: UserCredentialsDto) : UserDetails =
        User.builder()
            .username(dto.email)
            .password(dto.password)
            .roles(*dto.roles)
            .build()
}