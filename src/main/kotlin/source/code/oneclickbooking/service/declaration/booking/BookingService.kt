package source.code.oneclickbooking.service.declaration.booking

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.booking.BookingCreateDto
import source.code.oneclickbooking.dto.request.booking.BookingUpdateStatusDto
import source.code.oneclickbooking.dto.response.booking.BookingDetailedResponseDto
import source.code.oneclickbooking.dto.response.booking.BookingResponseDto
import source.code.oneclickbooking.model.BookingStatus

interface BookingService {
    fun create(bookingDto: BookingCreateDto): BookingResponseDto
    fun update(id: Int, patch: JsonMergePatch): BookingResponseDto
    fun delete(id: Int)
    fun updateStatus(id: Int, statusDto: BookingUpdateStatusDto): BookingResponseDto
    fun get(id: Int): BookingResponseDto
    fun getAll(): List<BookingResponseDto>
    fun getFiltered(filter: FilterDto): List<BookingDetailedResponseDto>
}