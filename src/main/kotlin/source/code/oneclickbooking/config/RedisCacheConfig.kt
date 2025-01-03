package source.code.oneclickbooking.config

import  com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@EnableAutoConfiguration(exclude = [RedisRepositoriesAutoConfiguration::class])
@Configuration
@EnableCaching
class RedisCachingConfig {

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): CacheManager {
        val objectMapper = ObjectMapper().apply {
            registerModule(com.fasterxml.jackson.module.kotlin.kotlinModule())
            registerModule(JavaTimeModule())
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                    .allowIfBaseType(Any::class.java)
                    .build(),
                ObjectMapper.DefaultTyping.EVERYTHING
            )
        }

        val defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(60))
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(Jackson2JsonRedisSerializer(
                    objectMapper,
                    Any::class.java
                ))
            )

        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(defaultCacheConfig)
            .build()
    }
}
