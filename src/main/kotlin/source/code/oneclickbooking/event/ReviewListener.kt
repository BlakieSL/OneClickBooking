package source.code.oneclickbooking.event

import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("!test")
class ReviewListener(private val cacheManager: CacheManager) {
    @EventListener
    fun handleReviewEvent(event: ReviewEvent) {
        val review = event.review
        cacheManager.getCache("reviews")?.evict(review.id!!)
    }
}