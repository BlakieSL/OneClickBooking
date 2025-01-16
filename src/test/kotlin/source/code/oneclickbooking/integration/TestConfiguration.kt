package source.code.oneclickbooking.integration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.support.NoOpCacheManager
import org.springframework.context.annotation.Bean
import source.code.oneclickbooking.service.declaration.util.TimeProviderService
import java.time.LocalDateTime

@TestConfiguration
class TestConfiguration {
    @Bean
    fun cacheManager(): CacheManager {
        return NoOpCacheManager()
    }

    @Bean
    fun timeProviderService(): TimeProviderService {
        return object : TimeProviderService {
            override fun getCurrentTime(): LocalDateTime {
                return LocalDateTime.of(2025, 1, 16, 9, 0, 0)
            }
        }
    }
}