package source.code.oneclickbooking.service.implementation.util

import org.springframework.stereotype.Service
import source.code.oneclickbooking.service.declaration.util.TimeProviderService
import java.time.LocalDateTime

@Service
class TimeProviderServiceImpl : TimeProviderService {
    override fun getCurrentTime(): LocalDateTime = LocalDateTime.now()
}