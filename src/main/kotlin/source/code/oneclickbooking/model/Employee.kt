package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "employee")
class Employee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @Column(nullable = false)
    var username: String,

    var description: String? = null,
) {
    @OneToMany(
        mappedBy = "employee",
        cascade = [CascadeType.REMOVE],
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    val availabilities: MutableSet<EmployeeAvailability> = mutableSetOf()

    @OneToMany(mappedBy = "employee")
    val bookings: MutableSet<Booking> = mutableSetOf()

    @OneToMany(mappedBy = "employee", cascade = [CascadeType.REMOVE])
    val servicePointAssociations: MutableSet<ServicePointEmployee> = mutableSetOf()

    @ManyToMany(mappedBy = "employees")
    val treatments: MutableSet<Treatment> = mutableSetOf()

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

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Employee) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "Employee(id=$id, username='$username')"
    }
}