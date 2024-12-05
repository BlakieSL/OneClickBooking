package source.code.oneclickbooking.service.implementation.booking

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.request.BookingUpdateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.BookingMapper
import source.code.oneclickbooking.model.*
import source.code.oneclickbooking.repository.*
import source.code.oneclickbooking.service.declaration.BookingService
import source.code.oneclickbooking.service.declaration.JsonPatchService
import source.code.oneclickbooking.service.declaration.ValidationService

@Service
class BookingServiceImpl(
    private val validationService: ValidationService,
    private val jsonPatchService: JsonPatchService,
    private val mapper: BookingMapper,
    private val mappingResolver: BookingMappingResolver,
    private val repository: BookingRepository,
    private val userRepository: UserRepository,
    private val servicePointRepository: ServicePointRepository,
    private val employeeRepository: EmployeeRepository,
    private val treatmentRepository: TreatmentRepository
) : BookingService{
    override fun create(bookingDto: BookingCreateDto): BookingResponseDto {
        val user = findUser(bookingDto.userId)
        val servicePoint = findServicePoint(bookingDto.servicePointId)
        val employee = findEmployee(bookingDto.employeeId)
        val treatment = findTreatment(bookingDto.treatmentId)

        val booking = mapper.toEntity(
            bookingDto,
            user,
            servicePoint,
            employee,
            treatment
        )

        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    override fun update(id: Int, patch: JsonMergePatch): BookingResponseDto {
        val booking = find(id)
        val patched = applyPatch(booking, patch)

        validationService.validate(patched)
        mapper.update(booking, patched, mappingResolver)

        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    override fun delete(id: Int) {
        val booking = find(id)
        repository.delete(booking)
    }

    override fun get(id: Int): BookingResponseDto {
        return find(id).let { mapper.toResponseDto(it) }
    }

    override fun getAll(): List<BookingResponseDto> {
        return repository.findAll().map { mapper.toResponseDto(it) }
    }

    private fun applyPatch(booking: Booking, patch: JsonMergePatch): BookingUpdateDto {
        val responseDto = mapper.toResponseDto(booking)
        return jsonPatchService.applyPatch(patch, responseDto, BookingUpdateDto::class)
    }

    private fun find(id: Int) : Booking {
        return repository.findById(id)
            .orElseThrow{ RecordNotFoundException(Booking::class, id)}
    }

    private fun findUser(id: Int) : User {
        return userRepository.findById(id)
            .orElseThrow{ RecordNotFoundException(User::class, id)}
    }

    private fun findServicePoint(id: Int) : ServicePoint {
        return servicePointRepository.findById(id)
            .orElseThrow{ RecordNotFoundException(ServicePoint::class, id)}
    }

    private fun findEmployee(id: Int) : Employee {
        return employeeRepository.findById(id)
            .orElseThrow{ RecordNotFoundException(Employee::class, id)}
    }

    private fun findTreatment(id: Int) : Treatment {
        return treatmentRepository.findById(id)
            .orElseThrow{ RecordNotFoundException(Treatment::class, id)}
    }

}