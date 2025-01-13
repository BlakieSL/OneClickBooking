package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Entity
@Table(name = "booking")
class Booking (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @field:FutureOrPresent
    @Column(nullable = false)
    var date: LocalDateTime,

    @field:NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: BookingStatus = BookingStatus.PENDING
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

    @OneToOne(mappedBy = "booking", cascade = [CascadeType.ALL])
    var review: Review? = null


    companion object {
        fun createDefault(
            id: Int? = null,
            date: LocalDateTime = LocalDateTime.now().plusSeconds(1),
            user: User = User.createDefault(id=1),
            servicePoint: ServicePoint = ServicePoint.createDefault(id=1),
            employee: Employee = Employee.createDefault(id=1),
            treatment: Treatment = Treatment.createDefault(id=1),
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

enum class BookingStatus {
    PENDING,
    COMPLETED,
    CANCELLED
}