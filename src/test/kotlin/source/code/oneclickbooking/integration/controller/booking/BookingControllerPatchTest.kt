package source.code.oneclickbooking.integration.controller.booking

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.TestConfiguration
import source.code.oneclickbooking.integration.Utils
import source.code.oneclickbooking.integration.Utils.createBookingSql
import source.code.oneclickbooking.integration.annotation.SqlSetup
import java.time.DayOfWeek

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/schema/drop-schema.sql",
        "classpath:testcontainers/schema/create-schema.sql"
    ]
)
@Import(TestConfiguration::class)
@AutoConfigureMockMvc
@SpringBootTest
class BookingControllerPatchTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should update booking, When user is author, And Booking is Pending")
    @SqlSetup
    fun `test update should update booking when user is author and pending`() {
        Utils.setUserContext(userId = 1)

        val bookingId = 4
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), bookingId, "$date 15:00:00", 1, 1, 1, 1,"PENDING")

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(bookingId),
            jsonPath("$.date").value("${date}T10:00:00"),
            jsonPath("$.userId").value(1),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should update, When user is admin, And Booking is Pending")
    @SqlSetup
    fun `test update should update when user is admin`() {
        Utils.setAdminContext(userId = 3)
        val bookingId = 4
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 15:00:00", 1, 1, 1, 1,"PENDING")

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(4),
            jsonPath("$.date").value("${date}T10:00:00"),
            jsonPath("$.userId").value(1),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should update booking, When user is author, employee has CANCELLED booking on selected date")
    @SqlSetup
    fun `test update should update when employee has CANCELLED booking`() {
        Utils.setAdminContext(userId = 3)
        val bookingId = 4
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 15:00:00", 1, 1, 1, 1,"PENDING")
        jdbcTemplate.update(createBookingSql(), 5, "$date 10:00:00", 1, 1, 1, 1,"CANCELLED")

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(4),
            jsonPath("$.date").value("${date}T10:00:00"),
            jsonPath("$.userId").value(1),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should ignore illegal field")
    @SqlSetup
    fun `test update should ignore illegal field`() {
        Utils.setUserContext(userId = 2)

        val bookingId = 4
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), bookingId, "$date 15:00:00", 1, 1, 1, 2,"PENDING")

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(bookingId),
            jsonPath("$.date").value("${date}T10:00:00"),
            jsonPath("$.userId").value(2),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 404, When booking not found")
    @SqlSetup
    fun `test update should return 404 when booking not found`() {
        Utils.setUserContext(userId = 1)
        val bookingId = 100

        val requestBody = """
            {
                "date": "2025-01-01T00:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 403, When user is not author")
    @SqlSetup
    fun `test update should return 403 when user is not author`() {
        Utils.setUserContext(userId = 2)
        val bookingId = 1

        val requestBody = """
            {
                "date": "2025-01-01T00:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isForbidden)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When date is in the past")
    @SqlSetup
    fun `test update should return 400 when date is in the past`() {
        Utils.setUserContext(userId = 1)
        val bookingId = 1

        val requestBody = """
            {
                "date": "2020-01-01T00:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updating COMPLETED Booking")
    @SqlSetup
    fun `test update should return 400 when updating completed booking`() {
        Utils.setUserContext(userId = 1)
        val bookingId = 1

        val requestBody = """
            {
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updating CANCELLED Booking")
    @SqlSetup
    fun `test update should return 400 when updating cancelled booking`() {
        Utils.setUserContext(userId = 1)

        val bookingId = 4
        val date = Utils.getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "treatmentId": 2
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), bookingId, "$date 15:00:00", 1, 1, 1, 1,"CANCELLED")

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee does not provide treatment")
    @SqlSetup
    fun `test update should return 400 when updated employee does not provide treatment`() {
        Utils.setUserContext(userId = 1)
        val bookingId = 3

        val requestBody = """
            {
                "employeeId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not associated with service point")
    @SqlSetup
    fun `test update should return 400 when updated employee is not associated with service point`() {
        Utils.setUserContext(userId = 1)
        val bookingId = 3

        val requestBody = """
            {
                "employeeId": 3,
                "treatmentId": 3
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not available at the specified date")
    @SqlSetup
    fun `test update should return 400 when updated employee is not available at the specified date`() {
        Utils.setUserContext(userId = 1)
        val bookingId = 3

        val requestBody = """
            {
                "date": "2025-01-03T15:00:00",
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not available at specified time slot")
    @SqlSetup
    fun `test update should return 400 when updated employee is not available at specified time slot`() {
        Utils.setUserContext(userId = 1)
        val bookingId = 3

        val requestBody = """
            {
                "date": "2025-01-01T18:00:00",
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not available at specified time slot due to bookings")
    @SqlSetup
    fun `test update should return 400 when updated employee is not available at specified time slot due to bookings`() {
        Utils.setUserContext(userId = 1)

        val bookingId = 4
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T15:00:00",
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), bookingId, "$date 09:00:00", 1, 1, 1, 1,"PENDING")
        jdbcTemplate.update(createBookingSql(), 5, "$date 15:00:00", 1, 1, 1, 1,"PENDING")

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }
}