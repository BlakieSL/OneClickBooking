package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "image")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
class Image (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @Lob
    @Column(nullable = false, columnDefinition = "mediumblob")
    var image: ByteArray,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var parentType: EntityType,

    @field:NotNull
    @Column(name = "parent_id", nullable = false)
    var parentId: Int
)

enum class EntityType{
    REVIEW,
    EMPLOYEE,
    SERVICE_POINT
}