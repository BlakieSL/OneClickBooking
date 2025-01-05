package source.code.oneclickbooking.event

import org.springframework.context.ApplicationEvent
import source.code.oneclickbooking.model.Review

class ReviewEvent(
    source: Any,
    val review: Review
) : ApplicationEvent(source) {
}