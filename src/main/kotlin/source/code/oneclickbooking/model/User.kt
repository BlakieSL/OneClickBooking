package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import source.code.oneclickbooking.validation.email.UniqueEmailDomain

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
    @field:UniqueEmailDomain
    @Column(nullable = false, length = EMAIL_MAX_LENGTH)
    var email: String,

    @field:NotBlank
    @field:Size(min = BCRYPT_HASHED_PASSWORD_LENGTH, max = BCRYPT_HASHED_PASSWORD_LENGTH)
    @Column(nullable = false, length = BCRYPT_HASHED_PASSWORD_LENGTH)
    var password: String,

    @ManyToMany
    val roles: MutableSet<Role> = mutableSetOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val bookings: MutableSet<Booking> = mutableSetOf(),
) {
    companion object{
        const val NAME_MAX_LENGTH = 50
        const val SURNAME_MAX_LENGTH = 50
        const val EMAIL_MAX_LENGTH = 70
        const val BCRYPT_HASHED_PASSWORD_LENGTH = 60
        const val PASSWORD_MIN_LENGTH = 8
        const val PASSWORD_MAX_LENGTH = 50

        fun createDefault(
            id: Int? = null,
            name: String = "John",
            surname: String = "Doe",
            email: String = "john.doe@example.com",
            password: String = "hashed_password",
            roles: MutableSet<Role> = mutableSetOf(),
            bookings: MutableSet<Booking> = mutableSetOf()
        ): User {
            return User(
                id = id,
                name = name,
                surname = surname,
                email = email,
                password = password,
                roles = roles,
                bookings = bookings
            )
        }
    }
}