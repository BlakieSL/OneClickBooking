package source.code.oneclickbooking.controller

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import source.code.oneclickbooking.annotation.BookingOwnerOrAdmin
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.response.booking.BookingDetailedResponseDto
import source.code.oneclickbooking.dto.response.booking.BookingResponseDto
import source.code.oneclickbooking.service.declaration.booking.BookingService

@RestController
@RequestMapping("/api/bookings")
class BookingController(
    private val bookingService: BookingService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ResponseEntity<BookingResponseDto> =
        ResponseEntity.ok(bookingService.get(id))

    @GetMapping
    fun getAll(): ResponseEntity<List<BookingResponseDto>> =
        ResponseEntity.ok(bookingService.getAll())

    @PostMapping("/filtered")
    fun getFiltered(@Valid @RequestBody filter: FilterDto): ResponseEntity<List<BookingDetailedResponseDto>> =
        ResponseEntity.ok(bookingService.getFiltered(filter))

    @PostMapping
    fun create(@Valid @RequestBody request: BookingCreateDto): ResponseEntity<BookingResponseDto> =
        ResponseEntity.status(201).body(bookingService.create(request))

    @BookingOwnerOrAdmin
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Unit> =
        bookingService.delete(id).let { ResponseEntity.noContent().build() }

    @BookingOwnerOrAdmin
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody patch: JsonMergePatch
    ): ResponseEntity<BookingResponseDto> =
        ResponseEntity.ok (bookingService.update(id, patch))
}