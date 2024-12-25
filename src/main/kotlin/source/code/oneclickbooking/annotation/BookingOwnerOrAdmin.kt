package source.code.oneclickbooking.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("@authAnnotationService.isBookingOwner(#id)")
annotation class BookingOwnerOrAdmin()
