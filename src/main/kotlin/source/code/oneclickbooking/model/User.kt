package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @field:Size(max = NAME_MAX_LENGTH)
    @Column(nullable = false, length = NAME_MAX_LENGTH)
    var name: String,

    @field:NotBlank
    @field:Size(max = SURNAME_MAX_LENGTH)
    @Column(nullable = false, length = SURNAME_MAX_LENGTH)
    var surname: String,

    @field:NotBlank
    @field:Size(max = EMAIL_MAX_LENGTH)
    @field:Email
    @Column(nullable = false, length = EMAIL_MAX_LENGTH)
    var email: String,

    @field:NotBlank
    @field:Size(min = BCRYPT_HASHED_PASSWORD_LENGTH, max = BCRYPT_HASHED_PASSWORD_LENGTH)
    @Column(nullable = false, length = BCRYPT_HASHED_PASSWORD_LENGTH)
    var password: String,

    @ManyToMany
    val roles: MutableSet<Role> = mutableSetOf(),

    @OneToMany(mappedBy = "user")
    val bookings: MutableSet<Booking> = mutableSetOf(),
) {
    companion object{
        const val NAME_MAX_LENGTH = 50
        const val SURNAME_MAX_LENGTH = 50
        const val EMAIL_MAX_LENGTH = 70
        const val BCRYPT_HASHED_PASSWORD_LENGTH = 60
    }
}