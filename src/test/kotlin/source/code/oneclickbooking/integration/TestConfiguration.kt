package source.code.oneclickbooking.integration

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.support.NoOpCacheManager
import org.springframework.context.annotation.Bean

@TestConfiguration
class TestConfiguration {
    @Bean

    fun cacheManager(): CacheManager {
        return NoOpCacheManager()
    }
}