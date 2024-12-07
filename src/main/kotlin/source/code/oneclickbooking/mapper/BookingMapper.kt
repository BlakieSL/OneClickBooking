package source.code.oneclickbooking.mapper

import org.mapstruct.*
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.service.declaration.booking.BookingMappingResolverService

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
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "servicePoint", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "treatment", ignore = true)
    abstract fun toEntity(
        dto: BookingCreateDto,
        @Context mappingResolver: BookingMappingResolverService
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
        @Context mappingResolver: BookingMappingResolverService
    )

    @AfterMapping
    protected fun resolveCreate(
        @MappingTarget booking: Booking,
        dto: BookingCreateDto,
        @Context resolver: BookingMappingResolverService
    ) {
        booking.user = resolver.resolveUser(dto.userId)!!
        booking.servicePoint = resolver.resolveServicePoint(dto.servicePointId)!!
        booking.employee = resolver.resolveEmployee(dto.employeeId)!!
        booking.treatment = resolver.resolveTreatment(dto.treatmentId)!!
    }

    @AfterMapping
    protected fun resolveUpdate(
        @MappingTarget booking: Booking,
        dto: BookingUpdateDto,
        @Context resolver: BookingMappingResolverService
    ) {
        dto.userId?.let { booking.user = resolver.resolveUser(it)!! }
        dto.servicePointId?.let { booking.servicePoint = resolver.resolveServicePoint(it)!! }
        dto.employeeId?.let { booking.employee = resolver.resolveEmployee(it) }
        dto.treatmentId?.let { booking.treatment = resolver.resolveTreatment(it) }
    }
}