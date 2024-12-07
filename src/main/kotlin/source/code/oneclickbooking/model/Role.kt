package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "role")
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var name: RoleName,

    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<User> = mutableSetOf()
)

enum class RoleName {
    USER,
    ADMIN
}