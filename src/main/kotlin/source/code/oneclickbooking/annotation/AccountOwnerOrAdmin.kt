package source.code.oneclickbooking.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('ROLE_ADMIN') or #id == authentication.userId")
annotation class AccountOwnerOrAdmin()
