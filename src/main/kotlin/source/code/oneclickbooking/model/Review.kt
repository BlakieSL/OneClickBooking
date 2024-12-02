package source.code.oneclickbooking.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import org.jetbrains.annotations.NotNull

@Entity
data class Review (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @Column(nullable = false)
    var rating: Int,

    var text: String? = null,

    @OneToOne(mappedBy = "review")
    var booking: Booking,
) {
    companion object {
        fun createDefault(
            id: Int? = null,
            rating: Int = 5,
            text: String? = "Default Review Text",
            booking: Booking = Booking.createDefault()
        ): Review {
            return Review(
                id = id,
                rating = rating,
                text = text,
                booking = booking
            )
        }
    }
}