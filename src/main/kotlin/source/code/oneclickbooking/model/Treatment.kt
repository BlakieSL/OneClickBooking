package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import org.jetbrains.annotations.NotNull

@Entity
data class Treatment (
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

    @OneToMany(mappedBy = "treatment")
    val bookings: MutableSet<Booking> = mutableSetOf(),

    @ManyToMany
    val employees: MutableSet<Employee> = mutableSetOf(),
)