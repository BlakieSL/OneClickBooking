package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.DayOfWeek
import java.time.LocalTime

@Entity
@Table(name = "employee_availability")
class EmployeeAvailability (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @field:NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var dayOfWeek: DayOfWeek,

    @field:NotNull
    @Column(nullable = false)
    var startTime: LocalTime,

    @field:NotNull
    @Column(nullable = false)
    var endTime: LocalTime,

) {
    @ManyToOne
    @JoinColumn(name = "employee_id")
    lateinit var employee: Employee

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EmployeeAvailability) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "EmployeeAvailability(id=$id, dayOfWeek=$dayOfWeek, startTime=$startTime, endTime=$endTime)"
    }
}
