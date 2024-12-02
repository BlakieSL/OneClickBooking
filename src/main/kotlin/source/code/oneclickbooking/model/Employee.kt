package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
data class Employee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @Column(nullable = false)
    var username: String,

    var description: String? = null,

    @OneToMany(mappedBy = "employee")
    val availabilities: MutableSet<EmployeeAvailability> = mutableSetOf(),

    @OneToMany(mappedBy = "employee")
    val bookings: MutableSet<Booking> = mutableSetOf(),

    @OneToMany(mappedBy = "employee")
    val servicePointAssociations: MutableSet<ServicePointEmployee> = mutableSetOf(),

    @ManyToMany(mappedBy = "employees")
    val treatments: MutableSet<Treatment> = mutableSetOf(),
)