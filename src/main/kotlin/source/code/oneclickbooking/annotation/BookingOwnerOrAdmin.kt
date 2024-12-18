package source.code.oneclickbooking.annotation

import org.springframework.security.access.prepost.PreAuthorize
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("@authAnnotationService.isBookingOwner(#id)")
annotation class BookingOwnerOrAdmin()
