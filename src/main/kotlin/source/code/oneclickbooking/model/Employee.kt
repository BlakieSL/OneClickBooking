package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import lombok.NoArgsConstructor

@Entity
data class Employee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @Column(nullable = false)
    var username: String,

    var description: String? = null,

    @OneToMany(mappedBy = "employee", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val availabilities: MutableSet<EmployeeAvailability> = mutableSetOf(),

    @OneToMany(mappedBy = "employee")
    val bookings: MutableSet<Booking> = mutableSetOf(),

    @OneToMany(mappedBy = "employee", cascade = [CascadeType.REMOVE])
    val servicePointAssociations: MutableSet<ServicePointEmployee> = mutableSetOf(),

    @ManyToMany(mappedBy = "employees")
    val treatments: MutableSet<Treatment> = mutableSetOf(),
) {
    companion object {
        fun createDefault(
            id: Int? = null,
            username: String = "Default Employee",
            description: String? = null
        ): Employee {
            return Employee(
                id = id,
                username = username,
                description = description
            )
        }
    }
}