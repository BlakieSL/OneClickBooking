package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
class ServicePoint (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @Column(nullable = false)
    var name: String,

    @field:NotBlank
    @Column(nullable = false)
    var location: String,

    @field:NotBlank
    @field:Email
    @Column(nullable = false)
    var email: String,

    @field:NotBlank
    @Column(nullable = false)
    var phone: String,
) {
    @OneToMany(mappedBy = "servicePoint", cascade = [CascadeType.REMOVE])
    val bookings: MutableSet<Booking> = mutableSetOf()

    @OneToMany(mappedBy = "servicePoint", cascade = [CascadeType.REMOVE])
    val employeeAssociations: MutableSet<ServicePointEmployee> = mutableSetOf()

    companion object {
        fun createDefault(
            id: Int? = null,
            name: String = "Default Name",
            location: String = "Default Location",
            email: String = "default@example.com",
            phone: String = "123456789"
        ): ServicePoint {
            return ServicePoint(
                id = id,
                name = name,
                location = location,
                email = email,
                phone = phone
            )
        }
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ServicePoint) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "ServicePoint(id=$id, name='$name', email='$email')"
    }
}