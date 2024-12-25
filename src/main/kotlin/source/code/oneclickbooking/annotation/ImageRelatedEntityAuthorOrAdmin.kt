package source.code.oneclickbooking.annotation

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("@authAnnotationService.isImageRelatedEntityAuthorOrAdmin(#dto.parentType, #dto.parentId)")
annotation class ImageRelatedEntityAuthorOrAdmin ()

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("@authAnnotationService.isImageRelatedEntityAuthorOrAdminById(#id)")
annotation class ImageRelatedEntityAuthorOrAdminById()