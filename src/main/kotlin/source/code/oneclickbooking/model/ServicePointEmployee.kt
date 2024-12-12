package source.code.oneclickbooking.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ServicePointEmployee (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
) {
    @ManyToOne
    @JoinColumn(name = "service_point_id")
    lateinit var servicePoint: ServicePoint

    @ManyToOne
    @JoinColumn(name = "employee_id")
    lateinit var employee: Employee

    companion object {
        fun createDefault(
            id: Int? = null,
            servicePoint: ServicePoint = ServicePoint.createDefault(),
            employee: Employee = Employee.createDefault()
        ): ServicePointEmployee {
            return ServicePointEmployee().apply {
                this.id = id
                this.servicePoint = servicePoint
                this.employee = employee
            }
        }

        fun of(id: Int, servicePoint: ServicePoint, employee: Employee): ServicePointEmployee {
            return ServicePointEmployee().apply {
                this.id = id
                this.servicePoint = servicePoint
                this.employee = employee
            }
        }
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ServicePointEmployee) return false
        return id != null && id == other.id
    }

    override fun toString(): String {
        return "ServicePointEmployee(id=$id)"
    }
}