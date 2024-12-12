package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.NotNull

@Entity
class Treatment (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @Column(nullable = false)
    var name: String,

    @field:NotBlank
    @Column(nullable = false)
    var description: String,

    @field:NotNull
    @field:Positive
    @Column(nullable = false)
    var price: Double,

    @field:NotNull
    @field:Positive
    @Column(nullable = false)
    var duration: Int,
) {
    @OneToMany(mappedBy = "treatment")
    val bookings: MutableSet<Booking> = mutableSetOf()

    @ManyToMany
    val employees: MutableSet<Employee> = mutableSetOf()

    companion object {
        fun createDefault(
            id: Int? = null,
            name: String = "Default Treatment",
            description: String = "Default Description",
            price: Double = 100.0,
            duration: Int = 60
        ): Treatment {
            return Treatment(
                id = id,
                name = name,
                description = description,
                price = price,
                duration = duration
            )
        }
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Treatment) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "Treatment(id=$id, name='$name', price=$price, duration=$duration)"
    }
}