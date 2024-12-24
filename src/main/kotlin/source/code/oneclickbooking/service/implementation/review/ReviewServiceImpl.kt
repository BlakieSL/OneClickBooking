package source.code.oneclickbooking.service.implementation.review

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.other.FilterDto
import source.code.oneclickbooking.dto.request.ReviewCreateDto
import source.code.oneclickbooking.dto.request.ReviewUpdateDto
import source.code.oneclickbooking.dto.response.ReviewResponseDto
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.mapper.ReviewMapper
import source.code.oneclickbooking.model.Review
import source.code.oneclickbooking.repository.ReviewRepository
import source.code.oneclickbooking.service.declaration.review.ReviewService
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import source.code.oneclickbooking.service.declaration.util.ValidationService
import source.code.oneclickbooking.specification.ReviewSpecification
import source.code.oneclickbooking.specification.SpecificationBuilder
import source.code.oneclickbooking.specification.SpecificationFactory

@Service
class ReviewServiceImpl(
    private val jsonPatchService: JsonPatchService,
    private val validationService: ValidationService,
    private val mapper: ReviewMapper,
    private val repository: ReviewRepository
): ReviewService {
    @Transactional
    override fun create(reviewDto: ReviewCreateDto): ReviewResponseDto {
        val review = mapper.toEntity(reviewDto)
        val savedReview = repository.save(review)
        return mapper.toResponseDto(savedReview)
    }

    @Transactional
    override fun update(id: Int, patch: JsonMergePatch): ReviewResponseDto {
        val review = find(id)
        val patched = applyPatch(patch)

        validationService.validate(patched)
        mapper.update(review, patched)

        val savedReview = repository.save(review)
        return mapper.toResponseDto(savedReview)
    }

    @Transactional
    override fun delete(id: Int) {
        val review = find(id)
        review.booking.review = null
        repository.delete(review)
    }

    override fun get(id: Int): ReviewResponseDto {
        return find(id).let { mapper.toResponseDto(it) }
    }

    override fun getAll(): List<ReviewResponseDto> {
        return repository.findAll().map { mapper.toResponseDto(it) }
    }

    override fun getFiltered(filter: FilterDto): List<ReviewResponseDto> {
        val reviewFactory = SpecificationFactory { criteria -> ReviewSpecification(criteria) }
        val builder = SpecificationBuilder(filter, reviewFactory)
        val specification = builder.build()
        return repository.findAll(specification).map { mapper.toResponseDto(it) }
    }

    private fun applyPatch(patch: JsonMergePatch): ReviewUpdateDto {
        return jsonPatchService.applyPatch(patch, ReviewUpdateDto(), ReviewUpdateDto::class)
    }

    private fun find(id: Int): Review {
        return repository.findById(id).orElseThrow {
            RecordNotFoundException(Review::class, id)
        }
    }
}