package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "role")
class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotBlank
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var name: RoleName,

) {
    @ManyToMany(mappedBy = "roles")
    val users: MutableSet<User> = mutableSetOf()

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Role) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "Role(id=$id, name=$name)"
    }
}

enum class RoleName {
    USER,
    ADMIN
}