package source.code.oneclickbooking.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.DayOfWeek
import java.time.LocalTime

@Entity
data class EmployeeAvailability (
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

    @ManyToOne
    @JoinColumn(name = "employee_id")
    var employee: Employee,
)
