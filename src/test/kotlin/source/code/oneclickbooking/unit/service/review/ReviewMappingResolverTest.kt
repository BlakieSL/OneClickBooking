package source.code.oneclickbooking.unit.service.review

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.model.Booking
import source.code.oneclickbooking.repository.BookingRepository
import source.code.oneclickbooking.service.implementation.review.ReviewMappingResolverImpl
import java.util.*

@ExtendWith(MockitoExtension::class)
class ReviewMappingResolverTest {
    @Mock
    private lateinit var bookingRepository: BookingRepository

    @InjectMocks
    private lateinit var resolver: ReviewMappingResolverImpl

    private lateinit var booking: Booking

    @BeforeEach
    fun setUp() {


        booking = Booking.createDefault(id = 1)
    }

    @Test
    fun `should resolve booking by id`() {
        whenever(bookingRepository.findById(1)).thenReturn(Optional.of(booking))

        val result = resolver.resolveBooking(1)

        assertEquals(booking, result)
    }

    @Test
    fun `should throw exception when booking is not found`() {
        whenever(bookingRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows<RecordNotFoundException> {
            resolver.resolveBooking(1)
        }
    }
}