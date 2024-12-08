package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull


@Entity
data class Review (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @Column(nullable = false)
    var rating: Int,

    var text: String? = null,

    @field:NotNull
    @OneToOne(mappedBy = "review")
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
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