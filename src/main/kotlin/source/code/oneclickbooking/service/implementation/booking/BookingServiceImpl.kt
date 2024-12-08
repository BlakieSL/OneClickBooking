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
import source.code.oneclickbooking.service.declaration.booking.BookingService
import source.code.oneclickbooking.service.declaration.JsonPatchService
import source.code.oneclickbooking.service.declaration.ValidationService
import source.code.oneclickbooking.service.declaration.booking.BookingMappingResolverService

@Service
class BookingServiceImpl(
    private val validationService: ValidationService,
    private val jsonPatchService: JsonPatchService,
    private val mapper: BookingMapper,
    private val repository: BookingRepository,
) : BookingService {
    override fun create(bookingDto: BookingCreateDto): BookingResponseDto {
        val booking = mapper.toEntity(bookingDto)
        val savedBooking = repository.save(booking)
        return mapper.toResponseDto(savedBooking)
    }

    override fun update(id: Int, patch: JsonMergePatch): BookingResponseDto {
        val booking = find(id)
        val patched = applyPatch(booking, patch)

        validationService.validate(patched)
        mapper.update(booking, patched)

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
            .orElseThrow{ RecordNotFoundException(Booking::class, id) }
    }
}