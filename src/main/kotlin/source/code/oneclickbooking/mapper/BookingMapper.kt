package source.code.oneclickbooking.mapper

import org.mapstruct.*
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.service.implementation.booking.BookingMappingResolver

@Mapper(componentModel = "spring")
abstract class BookingMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "servicePointId", source = "servicePoint.id")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "treatmentId", source = "treatment.id")
    @Mapping(target = "reviewId", source = "review.id")
    abstract fun toResponseDto(booking: Booking) : BookingResponseDto

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "review", ignore = true)
    abstract fun toEntity(
        dto: BookingCreateDto,
        user: User,
        servicePoint: ServicePoint,
        employee: Employee,
        treatment: Treatment,
    ) : Booking

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "review", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "servicePoint", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "treatment", ignore = true)
    abstract fun update(
        @MappingTarget booking: Booking,
        dto: BookingUpdateDto,
        @Context mappingResolver: BookingMappingResolver
    )

    @AfterMapping
    protected fun resolve(
        @MappingTarget booking: Booking,
        dto: BookingUpdateDto,
        @Context resolver: BookingMappingResolver
    ) {
        dto.userId?.let { booking.user = resolver.resolveUser(it)!! }
        dto.servicePointId?.let { booking.servicePoint = resolver.resolveServicePoint(it)!! }
        dto.employeeId?.let { booking.employee = resolver.resolveEmployee(it) }
        dto.treatmentId?.let { booking.treatment = resolver.resolveTreatment(it) }
    }
}