package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull


@Entity
@Table(name = "review")
class Review (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @Column(nullable = false)
    var rating: Int,

    var text: String? = null,
) {
    @field:NotNull
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    lateinit var booking: Booking

    companion object {
        fun createDefault(
            id: Int? = null,
            rating: Int = 5,
            text: String? = "Default Review Text",
            booking: Booking = Booking.createDefault()
        ): Review {
            return Review(id = id, rating = rating, text = text,).apply {
                this.booking = booking
            }
        }

        fun of(id: Int? = null, rating: Int, text: String? = null, booking: Booking): Review {
            return Review(id = id, rating = rating, text = text,).apply {
                this.booking = booking
            }
        }
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Review) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "Review(id=$id, rating=$rating)"
    }
}