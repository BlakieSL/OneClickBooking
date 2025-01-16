package source.code.oneclickbooking.service.declaration.util

import java.time.LocalDateTime

interface TimeProviderService {
    fun getCurrentTime(): LocalDateTime
}