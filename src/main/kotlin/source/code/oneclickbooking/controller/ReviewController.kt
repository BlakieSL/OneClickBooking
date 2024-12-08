package source.code.oneclickbooking.controller

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.validation.Valid
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.service.declaration.review.ReviewService

@RestController
@RequestMapping("/api/reviews")
class ReviewController(
    private val reviewService: ReviewService
) {
    @GetMapping("/id")
    fun get(@PathVariable id: Int): ResponseEntity<ReviewResponseDto> =
        ResponseEntity.ok(reviewService.get(id))

    @GetMapping
    fun getAll(): ResponseEntity<List<ReviewResponseDto>> =
        ResponseEntity.ok(reviewService.getAll())

    @PostMapping
    fun create(@Valid @RequestBody request: ReviewCreateDto): ResponseEntity<ReviewResponseDto> =
        ResponseEntity.status(201).body(reviewService.create(request))

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Unit> =
        reviewService.delete(id).let { ResponseEntity.noContent ().build() }

    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody patch: JsonMergePatch
    ): ResponseEntity<ReviewResponseDto> =
        ResponseEntity.ok(reviewService.update(id, patch))
}