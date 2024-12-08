package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
data class ServicePoint (
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

    @OneToMany(mappedBy = "servicePoint", cascade = [CascadeType.REMOVE])
    val bookings: MutableSet<Booking> = mutableSetOf(),

    @OneToMany(mappedBy = "servicePoint", cascade = [CascadeType.REMOVE])
    val employeeAssociations: MutableSet<ServicePointEmployee> = mutableSetOf()
) {
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
}