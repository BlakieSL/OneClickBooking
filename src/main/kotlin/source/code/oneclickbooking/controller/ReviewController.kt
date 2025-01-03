package source.code.oneclickbooking.controller

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import source.code.oneclickbooking.annotation.ReviewOwnerOrAdmin
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.dto.response.ReviewSummaryResponseDto
import source.code.oneclickbooking.service.declaration.review.ReviewService

@RestController
@RequestMapping("/api/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ResponseEntity<ReviewResponseDto> =
        ResponseEntity.ok(reviewService.get(id))

    @GetMapping
    fun getAll(): ResponseEntity<List<ReviewResponseDto>> =
        ResponseEntity.ok(reviewService.getAll())

    @PostMapping("/filtered")
    fun getFiltered(@RequestBody filter: FilterDto): ResponseEntity<ReviewSummaryResponseDto> =
        ResponseEntity.ok(reviewService.getFiltered(filter))

    @PostMapping
    fun create(@Valid @RequestBody request: ReviewCreateDto): ResponseEntity<ReviewResponseDto> =
        ResponseEntity.status(201).body(reviewService.create(request))

    @ReviewOwnerOrAdmin
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Unit> =
        reviewService.delete(id).let { ResponseEntity.noContent ().build() }

    @ReviewOwnerOrAdmin
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody patch: JsonMergePatch
    ): ResponseEntity<ReviewResponseDto> =
        ResponseEntity.ok(reviewService.update(id, patch))
}