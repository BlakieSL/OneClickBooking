package source.code.oneclickbooking.service.declaration.review

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.dto.response.ReviewSummaryResponseDto

interface ReviewService {
    fun create(reviewDto: ReviewCreateDto): ReviewResponseDto
    fun update(id: Int, patch: JsonMergePatch): ReviewResponseDto
    fun delete(id: Int)
    fun get(id: Int): ReviewResponseDto
    fun getAll(): List<ReviewResponseDto>
    fun getFiltered(filter: FilterDto): ReviewSummaryResponseDto
}