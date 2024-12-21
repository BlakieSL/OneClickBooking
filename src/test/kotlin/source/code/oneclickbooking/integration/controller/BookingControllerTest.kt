package source.code.oneclickbooking.integration.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import source.code.oneclickbooking.integration.annotation.SqlSetup

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    scripts = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql"
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class BookingControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/bookings/{id} should return booking")
    @SqlSetup
    fun `test get should return booking`() {
        LOGGER.info("RUNNING test get booking by id should return booking...")

        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(bookingId))
            .andExpect(jsonPath("$.date").value("2022-01-01T00:00:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(1))
            .andExpect(jsonPath("$.treatmentId").value(1))

        LOGGER.info("test get booking by id should return booking PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/bookings/{id} should return 404 when booking not found")
    @SqlSetup
    fun `test get should return 404 when booking not found`() {
        LOGGER.info("RUNNING test get booking by id should return 404 when booking not found...")
        val bookingId = 100

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)

        LOGGER.info("test get booking by id should return 404 when booking not found PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/bookings/{id} should return 400 when id not a number")
    fun `test get should return 400 when id not a number`() {
        LOGGER.info("RUNNING test get booking by id should return 400 when id not a number...")

        mockMvc.perform(get("/api/bookings/abc"))
            .andExpect(status().isBadRequest)

        LOGGER.info("test get booking by id should return 400 when id not a number PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/bookings should return all bookings")
    @SqlSetup
    fun `test get all should return all bookings`() {
        LOGGER.info("RUNNING test get all bookings should return all bookings...")

        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].date").value("2022-01-01T00:00:00"))
            .andExpect(jsonPath("$[0].userId").value(1))
            .andExpect(jsonPath("$[0].servicePointId").value(1))
            .andExpect(jsonPath("$[0].employeeId").value(1))
            .andExpect(jsonPath("$[0].treatmentId").value(1))

        LOGGER.info("test get all bookings should return all bookings PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test GET /api/bookings should return empty list when no bookings")
    fun `test get all should return empty list when no bookings`() {
        LOGGER.info("RUNNING test get all bookings should return empty list when no bookings...")

        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))

        LOGGER.info("test get all bookings should return empty list when no bookings PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should create booking")
    @SqlSetup
    fun `test create should create booking`() {
        LOGGER.info("RUNNING test create booking should create booking...")

        val requestBody = """
            {
                "date": "2025-01-02T10:00:00",
                "userId": 1,
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.date").value("2025-01-02T10:00:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(1))
            .andExpect(jsonPath("$.treatmentId").value(1))

        LOGGER.info("test create booking should create booking PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should create booking, when selected employee " +
            "have bookings on the selected date")
    @SqlSetup
    fun `test create should create booking when employee has bookings`() {
        LOGGER.info("RUNNING test create booking should create booking, when selected employee " +
                "have bookings on the selected date...")

        val requestBody = """
            {
                "date": "2025-01-06T14:00:00",
                "userId": 1,
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.date").value("2025-01-06T14:00:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(1))
            .andExpect(jsonPath("$.treatmentId").value(1))

        LOGGER.info("test create should create booking when employee has bookings PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should create booking, when selected employee " +
            "have splitted availability on selected date")
    @SqlSetup
    fun `test create should create booking when employee has splitted availability`() {
        LOGGER.info("RUNNING test create booking should create booking, when selected employee " +
                "have splitted availability on selected date...")

        val requestBody = """
            {
                "date": "2025-01-06T15:00:00",
                "userId": 1,
                "servicePointId": 1,
                "employeeId": 2,
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.date").value("2025-01-06T15:00:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(2))
            .andExpect(jsonPath("$.treatmentId").value(2))

        LOGGER.info("test create should create booking when employee has splitted availability PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should should create booking, when selected employee " +
            "have splitted availability on selected date and bookings")
    @SqlGroup(
        Sql(
            scripts = ["classpath:testcontainers/insert-data.sql",
                      "classpath:testcontainers/booking/insert-booking.sql"],
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
        ),
        Sql(
            scripts = ["classpath:testcontainers/remove-data.sql"],
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
        )
    )
    fun `test create should create booking when employee has splitted availability and bookings`() {
        LOGGER.info("RUNNING test create booking should create booking, when selected employee " +
                "have splitted availability on selected date and bookings...")

        val requestBody = """
            {
                "date": "2025-01-06T15:15:00",
                "userId": 1,
                "servicePointId": 1,
                "employeeId": 2,
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(5))
            .andExpect(jsonPath("$.date").value("2025-01-06T15:15:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(2))
            .andExpect(jsonPath("$.treatmentId").value(2))

        LOGGER.info("test create should create booking when employee has splitted availability" +
                " and bookings PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should return 400 when employee does not provide treatment")
    @SqlSetup
    fun `test create should return 400 when employee does not provide treatment`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should return 400 when employee is not associated " +
            "with service point")
    @SqlSetup
    fun `test create should return 400 when employee is not associated with service point`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should return 400 when employee is not available " +
            "at the specified date")
    @SqlSetup
    fun `test create should return 400 when employee is not available at the specified date`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should return 400 when employee is not available " +
            "at specified time slot")
    @SqlSetup
    fun `test create should return 400 when employee is not available at specified time slot`() {

    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should return 400 when date is in the past")
    fun `test create should return 400 when date is in the past`() {
        LOGGER.info("RUNNING test create booking should return 400 when date is in the past...")

        val requestBody = """
            {
                "date": "2020-01-01T00:00:00",
                "userId": 1,
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        LOGGER.info("test create booking should return 400 when date is in the past PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("Test POST /api/bookings should return 404 when provided service point not found")
    @SqlSetup
    fun `test create should return 404 when provided service point not found`() {
        LOGGER.info("RUNNING test create booking should return 404 when provided service point not found...")

        val requestBody = """
            {
                "date": "2025-01-02T10:00:00",
                "userId": 1,
                "servicePointId": 100,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isNotFound)

        LOGGER.info("test create booking should return 404 when provided service point not found PASSED!")
    }

    @Test
    @DisplayName("Test DELETE /api/bookings/{id} should delete booking when user is author")
    @SqlSetup
    fun `test delete should delete booking when user is author`() {
        LOGGER.info("RUNNING test delete booking should delete booking...")

        setUserContext(userId = 1)
        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)


        LOGGER.info("test delete booking should delete booking PASSED!")
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["ADMIN"])
    @DisplayName("Test DELETE /api/bookings/{id} should delete booking when user is admin")
    @SqlSetup
    fun `test delete should delete booking when user is admin`() {
        LOGGER.info("RUNNING test delete booking should delete booking when user is admin...")

        setAdminContext(userId = 3)
        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)

        LOGGER.info("test delete booking should delete booking when user is admin PASSED!")
    }

    @Test
    @DisplayName("Test DELETE /api/bookings/{id} should return 404 when booking not found")
    @SqlSetup
    fun `test delete should return 404 when booking not found`() {
        LOGGER.info("RUNNING test delete booking should return 404 when booking not found...")

        setUserContext(userId = 1)
        val bookingId = 100

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)

        LOGGER.info("test delete booking should return 404 when booking not found PASSED!")
    }

    @Test
    @DisplayName("Test DELETE /api/bookings/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test delete should return 403 when user is not author`() {
        LOGGER.info("RUNNING test delete booking should return 403 when user is not author...")

        setUserContext(userId = 2)
        val bookingId = 1

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isForbidden)

        LOGGER.info("test delete booking should return 403 when user is not author PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should update booking when user is author")
    @SqlSetup
    fun `test update should update booking when user is author`() {
        LOGGER.info("RUNNING test update booking should update booking...")

        setUserContext(userId = 1)
        val bookingId = 1

        val requestBody = """
            {
                "date": "2025-01-01T00:00:00",
                "userId": 1,
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.date").value("2025-01-01T00:00:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(1))
            .andExpect(jsonPath("$.treatmentId").value(1))

        LOGGER.info("test update booking should update booking PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should update when user is admin")
    @SqlSetup
    fun `test update should update when user is admin`() {
        LOGGER.info("RUNNING test update booking should update when user is admin...")

        setAdminContext(userId = 3)
        val bookingId = 1

        val requestBody = """
            {
                "date": "2025-01-01T00:00:00",
                "userId": 1,
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.date").value("2025-01-01T00:00:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(1))
            .andExpect(jsonPath("$.treatmentId").value(1))

        LOGGER.info("test update booking should update when user is admin PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should return 404 when booking not found")
    @SqlSetup
    fun `test update should return 404 when booking not found`() {
        LOGGER.info("RUNNING test update booking should return 404 when booking not found...")

        setUserContext(userId = 1)
        val bookingId = 100

        val requestBody = """
            {
                "date": "2025-01-01T00:00:00",
                "userId": 1,
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

        LOGGER.info("test update booking should return 404 when booking not found PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should return 403 when user is not author")
    @SqlSetup
    fun `test update should return 403 when user is not author`() {
        LOGGER.info("RUNNING test update booking should return 403 when user is not author...")

        setUserContext(userId = 2)
        val bookingId = 1

        val requestBody = """
            {
                "date": "2025-01-01T00:00:00",
                "userId": 1,
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

        LOGGER.info("test update booking should return 403 when user is not author PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should return 400 when date is in the past")
    @SqlSetup
    fun `test update should return 400 when date is in the past`() {
        LOGGER.info("RUNNING test update booking should return 400 when date is in the past...")

        setUserContext(userId = 1)
        val bookingId = 1

        val requestBody = """
            {
                "date": "2020-01-01T00:00:00",
                "userId": 1,
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

        LOGGER.info("test update booking should return 400 when date is in the past PASSED!")
    }

    @Test
    @DisplayName("Test PATCH /api/bookings/{id} should ignore illegal field")
    @SqlSetup
    fun `test update should ignore illegal field`() {
        LOGGER.info("RUNNING test update booking should ignore illegal field...")

        setUserContext(userId = 1)
        val bookingId = 1

        val requestBody = """
            {
                "date": "2025-01-01T00:00:00",
                "illegalField": "illegalValue",
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.date").value("2025-01-01T00:00:00"))
            .andExpect(jsonPath("$.userId").value(1))
            .andExpect(jsonPath("$.servicePointId").value(1))
            .andExpect(jsonPath("$.employeeId").value(1))
            .andExpect(jsonPath("$.treatmentId").value(2))

        LOGGER.info("test update booking should return smth when illegal field provided PASSED!")
    }

    private fun setUserContext(userId: Int) {
        setContext(userId, "ROLE_USER")
    }

    private fun setAdminContext(userId: Int) {
        setContext(userId, "ROLE_ADMIN")
    }

    private fun setContext(userId: Int, role: String) {
        val customAuth = CustomAuthenticationToken(
            principal = "testuser",
            userId = userId,
            credentials = null,
            authorities = listOf(SimpleGrantedAuthority(role))
        )
        SecurityContextHolder.getContext().authentication = customAuth
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BookingControllerTest::class.java)
    }
}