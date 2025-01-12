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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
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
    @DisplayName("GET /api/bookings/{id} - Should return booking")
    @SqlSetup
    fun `test get should return booking`() {
        logRunning()

        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.id").value(bookingId),
                jsonPath("$.date").value("2022-01-01T00:00:00"),
                jsonPath("$.userId").value(1),
                jsonPath("$.servicePointId").value(1),
                jsonPath("$.employeeId").value(1),
                jsonPath("$.treatmentId").value(1)
            )

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/{id} - Should return 404 when booking not found")
    @SqlSetup
    fun `test get should return 404 when booking not found`() {
        logRunning()

        val bookingId = 100

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/{id} - Should return 400 when id not a number")
    fun `test get should return 400 when id not a number`() {
        logRunning()

        mockMvc.perform(get("/api/bookings/abc"))
            .andExpect(status().isBadRequest)

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings - Should return all bookings")
    @SqlSetup
    fun `test get all should return all bookings`() {
        logRunning()

        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(3),
                jsonPath("$[0].id").value(1),
                jsonPath("$[0].date").value("2022-01-01T00:00:00"),
                jsonPath("$[0].userId").value(1),
                jsonPath("$[0].servicePointId").value(1),
                jsonPath("$[0].employeeId").value(1),
                jsonPath("$[0].treatmentId").value(1)
            )

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings - Should return empty list, When no bookings")
    fun `test get all should return empty list when no bookings`() {
        logRunning()

        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/filtered - Should return filtered bookings by user")
    @SqlSetup
    fun `test get filtered should return filtered bookings by user`() {
        val requestBody = """
            {
                "filterCriteria": [
                    {
                        "filterKey": "USER",
                        "value": "1",
                        "operation": "EQUAL"
                    }
                ],
                "dataOption": "AND"
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(3),

                jsonPath("$[0].id").value(1),
                jsonPath("$[0].date").value("2022-01-01T00:00:00"),
                jsonPath("$[0].userId").value(1),

                jsonPath("$[0].servicePoint.id").value(1),
                jsonPath("$[0].servicePoint.location").value("test_location1"),
                jsonPath("$[0].servicePoint.name").value("test_name1"),

                jsonPath("$[0].employee.id").value(1),
                jsonPath("$[0].employee.username").value("test_username1"),

                jsonPath("$[0].treatmentId").value(1),
                jsonPath("$[0].reviewId").value(1)
            )
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/filtered - Should return 400 when filter key is invalid")
    fun `test get filtered should return 400 when filter key is invalid`() {
        val requestBody = """
            {
                "filterCriteria": [
                    {
                        "filterKey": "INVALID",
                        "value": "1",
                        "filterOperation": "EQUAL",
                    }
                ],
            }
        """.trimIndent()
        mockMvc.perform(
            post("/api/bookings/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/filtered - Should return 400 when filter operation is invalid")
    fun `test get filtered should return 400 when filter operation is invalid`() {
        val requestBody = """
            {
                "filterCriteria": [
                    {
                        "filterKey": "USER",
                        "value": "1",
                        "filterOperation": "INVALID",
                    }
                ],
            }
        """.trimIndent()
        mockMvc.perform(
            post("/api/bookings/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/filtered - Should return 400 when request body is invalid")
    fun `test get filtered should return 400 when request body is invalid`() {
        val requestBody = """
            {
                "filterCriteria": [
                    {
                        "filterKey": "USER",
                        "value": "1",
                        "filterOperation": "EQUAL",
                    }
                ],
            }
        """.trimIndent()
        mockMvc.perform(
            post("/api/bookings/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @DisplayName("POST /api/bookings - Should create booking")
    @SqlSetup
    fun `test create should create booking`() {
        logRunning()
        setUserContext(userId = 1)

        val requestBody = """
            {
                "date": "2025-01-16T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated).andExpectAll(
            jsonPath("$.id").value(4),
            jsonPath("$.userId").value(1),
            jsonPath("$.date").value("2025-01-16T10:00:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1),
            jsonPath("$.status").value("PENDING")
        )

        logPassed()
    }

    @Test
    @DisplayName("POST /api/bookings - Should create booking, When selected employee " +
            "have bookings on the selected date")
    @SqlSetup
    fun `test create should create booking when employee has bookings`() {
        logRunning()
        setUserContext(userId = 1)

        val requestBody = """
            {
                "date": "2025-01-06T14:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated).andExpectAll(
            jsonPath("$.id").value(4),
            jsonPath("$.userId").value(1),
            jsonPath("$.date").value("2025-01-06T14:00:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )

        logPassed()
    }

    @Test
    @DisplayName("POST /api/bookings - Should create booking, When selected employee " +
            "have splitted availability on selected date")
    @SqlSetup
    fun `test create should create booking when employee has splitted availability`() {
        logRunning()
        setUserContext(userId = 1)

        val requestBody = """
            {
                "date": "2025-01-06T15:00:00",
                "servicePointId": 1,
                "employeeId": 2,
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated).andExpectAll(
            jsonPath("$.id").value(4),
            jsonPath("$.userId").value(1),
            jsonPath("$.date").value("2025-01-06T15:00:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(2),
            jsonPath("$.treatmentId").value(2)
        )

        logPassed()
    }

    @Test
    @DisplayName("POST /api/bookings - Should  create booking, When selected employee " +
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
        logRunning()
        setUserContext(userId = 1)

        val requestBody = """
            {
                "date": "2025-01-06T15:15:00",
                "servicePointId": 1,
                "employeeId": 2,
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated).andExpectAll(
            jsonPath("$.id").value(6),
            jsonPath("$.userId").value(1),
            jsonPath("$.date").value("2025-01-06T15:15:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(2),
            jsonPath("$.treatmentId").value(2)
        )

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee does not provide treatment")
    @SqlSetup
    fun `test create should return 400 when employee does not provide treatment`() {
        logRunning()

        val requestBody = """
            {
                "date": "2025-01-02T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 3
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not associated " +
            "with service point")
    @SqlSetup
    fun `test create should return 400 when employee is not associated with service point`() {
        logRunning()

        val requestBody = """
            {
                "date": "2025-01-02T10:00:00",
                "servicePointId": 2,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not available " +
            "at the specified date")
    @SqlSetup
    fun `test create should return 400 when employee is not available at the specified date`() {
        logRunning()

        val requestBody = """
            {
                "date": "2025-01-05T15:15:00",
                "servicePointId": 1,
                "employeeId": 2,
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not available " +
            "at specified time slot due to different availability")
    @SqlSetup
    fun `test create should return 400 when employee is not available at specified time slot`() {
        logRunning()

        val requestBody = """
            {
                "date": "2025-01-06T14:00:00",
                "servicePointId": 1,
                "employeeId": 2,
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not available " +
            "at specified time slot due bookings")
    @SqlSetup
    fun `test create should return 400 when employee is not available at specified time slot due bookings`() {
        logRunning()

        val requestBody = """
            {
                "date": "2025-01-06T15:00:00",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When date is in the past")
    fun `test create should return 400 when date is in the past`() {
         logRunning()

        val requestBody = """
            {
                "date": "2020-01-01T00:00:00",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 404, When provided service point not found")
    @SqlSetup
    fun `test create should return 404 when provided service point not found`() {
        logRunning()

        val requestBody = """
            {
                "date": "2025-01-16T10:00:00",
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

        logPassed()
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should delete booking - When user is author")
    @SqlSetup
    fun `test delete should delete booking when user is author`() {
        logRunning()

        setUserContext(userId = 1)
        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)

        logPassed()
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should delete booking, When user is admin")
    @SqlSetup
    fun `test delete should delete booking when user is admin`() {
        logRunning()

        setAdminContext(userId = 3)
        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)

        logPassed()
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should return 404, When booking not found")
    @SqlSetup
    fun `test delete should return 404 when booking not found`() {
        logRunning()

        setUserContext(userId = 1)
        val bookingId = 100

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)

        logPassed()
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should return 403, When user is not author")
    @SqlSetup
    fun `test delete should return 403 when user is not author`() {
        logRunning()

        setUserContext(userId = 2)
        val bookingId = 1

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isForbidden)

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should update booking, When user is author")
    @SqlSetup
    fun `test update should update booking when user is author`() {
        logRunning()

        setUserContext(userId = 1)
        val bookingId = 3

        val requestBody = """
            {
                "date": "2025-01-16T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(3),
            jsonPath("$.date").value("2025-01-16T10:00:00"),
            jsonPath("$.userId").value(1),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should update, When user is admin")
    @SqlSetup
    fun `test update should update when user is admin`() {
        logRunning()

        setAdminContext(userId = 3)
        val bookingId = 3

        val requestBody = """
            {
                "date": "2025-01-16T10:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(3),
            jsonPath("$.date").value("2025-01-16T10:00:00"),
            jsonPath("$.userId").value(1),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 404, When booking not found")
    @SqlSetup
    fun `test update should return 404 when booking not found`() {
        logRunning()

        setUserContext(userId = 1)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 403, When user is not author")
    @SqlSetup
    fun `test update should return 403 when user is not author`() {
        logRunning()

        setUserContext(userId = 2)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When date is in the past")
    @SqlSetup
    fun `test update should return 400 when date is in the past`() {
        logRunning()

        setUserContext(userId = 1)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should ignore illegal field")
    @SqlSetup
    fun `test update should ignore illegal field`() {
        logRunning()

        setUserContext(userId = 1)
        val bookingId = 3

        val requestBody = """
            {
                "date": "2025-01-09T10:00:00",
                "illegalField": "illegalValue",
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.id").value(3),
            jsonPath("$.date").value("2025-01-09T10:00:00"),
            jsonPath("$.userId").value(1),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(2)
        )

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updating past booking")
    @SqlSetup
    fun `test update should return 400 when updating past booking`() {
        logRunning()

        setUserContext(userId = 1)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee does " +
            "not provide treatment")
    @SqlSetup
    fun `test update should return 400 when updated employee does not provide treatment`() {
        logRunning()

        setUserContext(userId = 1)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not " +
            "associated with service point")
    @SqlSetup
    fun `test update should return 400 when updated employee is not associated with service point`() {
        logRunning()

        setUserContext(userId = 1)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not " +
            "available at the specified date")
    @SqlSetup
    fun `test update should return 400 when updated employee is not available at the specified date`() {
        logRunning()

        setUserContext(userId = 1)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not " +
            "available at specified time slot")
    @SqlSetup
    fun `test update should return 400 when updated employee is not available at specified time slot`() {
        logRunning()

        setUserContext(userId = 1)
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

        logPassed()
    }

    @Test
    @DisplayName("PATCH /api/bookings/{id} - Should return 400, When updated employee is not " +
            "available at specified time slot due to bookings")
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
    fun `test update should return 400 when updated employee is not available at specified time slot due to bookings`() {
        logRunning()

        setUserContext(userId = 1)
        val bookingId = 3

        val requestBody = """
            {
                "date": "2025-01-02T15:00:00",
            }
        """.trimIndent()

        mockMvc.perform(
            patch("/api/bookings/$bookingId")
                .contentType("application/merge-patch+json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)

        logPassed()
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

    private fun logRunning() {
        LOGGER.info("RUNNING...")
    }

    private fun logPassed() {
        LOGGER.info("PASSED!")
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BookingControllerTest::class.java)
    }
}