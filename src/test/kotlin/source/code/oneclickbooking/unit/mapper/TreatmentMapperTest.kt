package source.code.oneclickbooking.unit.mapper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import source.code.oneclickbooking.mapper.TreatmentMapper
import source.code.oneclickbooking.model.Treatment

@ExtendWith(MockitoExtension::class)
class TreatmentMapperTest {

    @InjectMocks
    private lateinit var treatmentMapper: TreatmentMapper

    private lateinit var treatment: Treatment

    @BeforeEach
    fun setUp() {
        treatment = Treatment.createDefault(id = 1)
    }

    @Test
    fun `should map Treatment to TreatmentResponseDto`() {
        val result = treatmentMapper.toResponseDto(treatment)

        assertEquals(treatment.id, result.id)
        assertEquals(treatment.name, result.name)
        assertEquals(treatment.description, result.description)
        assertEquals(treatment.price, result.price)
        assertEquals(treatment.duration, result.duration)
    }
}