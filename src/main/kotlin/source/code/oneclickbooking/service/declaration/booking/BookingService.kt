package source.code.oneclickbooking.service.declaration.booking

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import source.code.oneclickbooking.dto.request.BookingCreateDto
import source.code.oneclickbooking.dto.response.BookingResponseDto

interface BookingService {
    fun create(bookingDto: BookingCreateDto): BookingResponseDto
    fun update(id: Int, patch: JsonMergePatch): BookingResponseDto
    fun delete(id: Int)
    fun get(id: Int): BookingResponseDto
    fun getAll(): List<BookingResponseDto>
}