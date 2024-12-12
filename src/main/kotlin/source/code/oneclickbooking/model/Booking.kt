package source.code.oneclickbooking.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
class Booking (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @field:FutureOrPresent
    @Column(nullable = false)
    var date: LocalDateTime,
) {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    @ManyToOne
    @JoinColumn(name = "service_point_id", nullable = false)
    lateinit var servicePoint: ServicePoint

    @ManyToOne
    @JoinColumn(name = "employee_id")
    var employee: Employee? = null

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    var treatment: Treatment? = null

    @OneToOne(mappedBy = "booking")
    var review: Review? = null

    companion object {
        fun createDefault(
            id: Int? = null,
            date: LocalDateTime = LocalDateTime.now().plusSeconds(1),
            user: User = User.createDefault(),
            servicePoint: ServicePoint = ServicePoint.createDefault(),
            employee: Employee = Employee.createDefault(),
            treatment: Treatment = Treatment.createDefault(),
            review: Review? = null
        ): Booking {
            return Booking(id = id, date = date).apply {
                this.user = user
                this.servicePoint = servicePoint
                this.employee = employee
                this.treatment = treatment
                this.review = review
            }
        }

        fun of(
            id: Int? = null,
            date: LocalDateTime,
            user: User,
            servicePoint: ServicePoint,
            employee: Employee? = null,
            treatment: Treatment? = null,
            review: Review? = null
        ): Booking {
            return Booking(id = id, date = date).apply {
                this.user = user
                this.servicePoint = servicePoint
                this.employee = employee
                this.treatment = treatment
                this.review = review
            }
        }
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Booking) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "Booking(id=$id, date=$date)"
    }
}