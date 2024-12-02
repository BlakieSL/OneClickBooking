package source.code.oneclickbooking.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class ServicePointEmployee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "service_point_id")
    var servicePoint: ServicePoint,

    @ManyToOne
    @JoinColumn(name = "employee_id")
    var employee: Employee,
)