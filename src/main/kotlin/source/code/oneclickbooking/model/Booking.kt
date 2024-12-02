package source.code.oneclickbooking.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.validation.constraints.Past
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity
data class Booking (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @field:Past
    @Column(nullable = false)
    var date: LocalDateTime,

    @field:NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var user: User,

    @field:NotNull
    @ManyToOne
    @JoinColumn(name = "service_point_id", nullable = false)
    var servicePoint: ServicePoint,

    @field:NotNull
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    var employee: Employee,

    @field:NotNull
    @ManyToOne
    @JoinColumn(name = "treatment_id", nullable = false)
    var treatment: Treatment,

    @OneToOne
    @JoinColumn(name = "review_id")
    var review: Review? = null,
)