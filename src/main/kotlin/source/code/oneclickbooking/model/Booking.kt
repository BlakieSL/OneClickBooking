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
data class Booking (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @field:FutureOrPresent
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

    @ManyToOne
    @JoinColumn(name = "employee_id")
    var employee: Employee? = null,

    @ManyToOne
    @JoinColumn(name = "treatment_id")
    var treatment: Treatment? = null,

    @OneToOne(mappedBy = "booking")
    var review: Review? = null,
) {
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
            return Booking(
                id = id,
                date = date,
                user = user,
                servicePoint = servicePoint,
                employee = employee,
                treatment = treatment,
                review = review
            )
        }
    }
}