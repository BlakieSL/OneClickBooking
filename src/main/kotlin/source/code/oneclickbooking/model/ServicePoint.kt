package source.code.oneclickbooking.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import lombok.NoArgsConstructor

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

    @OneToMany(mappedBy = "servicePoint")
    val bookings: MutableSet<Booking> = mutableSetOf(),

    @OneToMany(mappedBy = "servicePoint")
    val employeeAssociations: MutableSet<ServicePointEmployee> = mutableSetOf()
) {
    companion object {
        fun createDefault(
            name: String = "Default Name",
            location: String = "Default Location",
            email: String = "default@example.com",
            phone: String = "123456789"
        ): ServicePoint {
            return ServicePoint(
                name = name,
                location = location,
                email = email,
                phone = phone
            )
        }
    }
}