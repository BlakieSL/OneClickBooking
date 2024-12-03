package source.code.oneclickbooking.model

import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class BookingTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun `should validate valid booking`() {
        val booking = Booking.createDefault()
        val violations = validator.validate(booking)
        assertTrue(violations.isEmpty())
    }

    @Test
    fun `should fail validation when date is in the past`() {
        val booking = Booking.createDefault(date = LocalDateTime.now().minusDays(1))
        val violations = validator.validate(booking)
        assertTrue(violations.isNotEmpty())
    }
}
