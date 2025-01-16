package source.code.oneclickbooking.integration.controller

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.shaded.org.bouncycastle.jce.provider.BrokenPBE.Util
import source.code.oneclickbooking.integration.TestConfiguration
import source.code.oneclickbooking.integration.Utils
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.service.implementation.util.TimeProviderServiceImpl
import source.code.oneclickbooking.specification.SpecificationBuilder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

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
class BookingControllerTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/{id} - Should return 404 when booking not found")
    @SqlSetup
    fun `test get should return 404 when booking not found`() {
        val bookingId = 100

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings/{id} - Should return 400 when id not a number")
    fun `test get should return 400 when id not a number`() {
        mockMvc.perform(get("/api/bookings/abc"))
            .andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings - Should return all bookings")
    @SqlSetup
    fun `test get all should return all bookings`() {
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("GET /api/bookings - Should return empty list, When no bookings")
    fun `test get all should return empty list when no bookings`() {
        mockMvc.perform(get("/api/bookings"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings/filtered - Should return filtered bookings by user")
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

                jsonPath("$[2].id").value(1),
                jsonPath("$[2].date").value("2022-01-01T00:00:00"),
                jsonPath("$[2].userId").value(1),

                jsonPath("$[2].servicePoint.id").value(1),
                jsonPath("$[2].servicePoint.location").value("test_location1"),
                jsonPath("$[2].servicePoint.name").value("test_name1"),

                jsonPath("$[2].employee.id").value(1),
                jsonPath("$[2].employee.username").value("test_username1"),

                jsonPath("$[2].treatmentId").value(1),
                jsonPath("$[2].reviewId").value(1)
            )
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings/filtered - Should return filtered bookings by status")
    @SqlSetup
    fun `test get filtered should return filtered bookings by status`() {
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "filterCriteria": [
                    {
                        "filterKey": "STATUS",
                        "value": "PENDING",
                        "operation": "EQUAL"
                    }
                ],
                "dataOption": "AND"
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 15:00:00", 1, 1, 1, 1, "PENDING")

        mockMvc.perform(
            post("/api/bookings/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(1),
                jsonPath("$[0].id").value(4),
            )
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings/filtered - Should return filtered bookings by status with sort by date_closest")
    @SqlSetup
    fun `test get filtered should return filtered bookings by status and sorted by date_closest`() {
        val requestBody = """
            {
                "filterCriteria": [
                    {
                        "filterKey": "STATUS",
                        "value": "PENDING",
                        "operation": "EQUAL"
                    }
                ],
                "sortOption": "DATE_CLOSEST",
                "dataOption": "AND"
            }
        """.trimIndent()

        val fixedDate = LocalDate.of(2025, 1, 16)
        jdbcTemplate.update(createBookingSql(), 4, "$fixedDate 15:00:00", 1, 1, 1, 1, "PENDING")
        jdbcTemplate.update(createBookingSql(), 5, "$fixedDate 10:00:00", 1, 1, 1, 1, "PENDING")
        jdbcTemplate.update(createBookingSql(), 6, "$fixedDate 12:00:00", 1, 1, 1, 1, "PENDING")

        mockMvc.perform(
            post("/api/bookings/filtered")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isOk)
            .andExpectAll(
                jsonPath("$.size()").value(3),
                jsonPath("$[0].id").value(5),
                jsonPath("$[1].id").value(6),
                jsonPath("$[2].id").value(4),
            )
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings/filtered - Should return 400 when filter key is invalid")
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
    @DisplayName("POST /api/bookings/filtered - Should return 400 when filter operation is invalid")
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
    @DisplayName("POST /api/bookings/filtered - Should return 400 when request body is invalid")
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
        Utils.setUserContext(userId = 1)

        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
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
            jsonPath("$.date").value("${date}T10:00:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1),
            jsonPath("$.status").value("PENDING")
        )
    }

    @Test
    @DisplayName("POST /api/bookings - Should create booking, When selected employee has bookings on the selected date")
    @SqlSetup
    fun `test create should create booking when employee has bookings`() {
        Utils.setUserContext(userId = 1)

        val date = Utils.getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "date": "${date}T14:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 15:00:00", 1, 1, 1, 1,"PENDING")

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated).andExpectAll(
            jsonPath("$.id").value(5),
            jsonPath("$.userId").value(1),
            jsonPath("$.date").value("${date}T14:00:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )
    }

    @Test
    @DisplayName("POST /api/bookings - Should create booking, When selected employee has CANCELLED booking on the selected date")
    @SqlSetup
    fun `test create should create booking when employee has CANCELLED booking`() {
        Utils.setUserContext(userId = 1)

        val date = Utils.getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "date": "${date}T14:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 14:00:00", 1, 1, 1, 1,"CANCELLED")

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated).andExpectAll(
            jsonPath("$.id").value(5),
            jsonPath("$.userId").value(1),
            jsonPath("$.date").value("${date}T14:00:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(1),
            jsonPath("$.treatmentId").value(1)
        )
    }

    @Test
    @DisplayName("POST /api/bookings - Should create booking, When selected employee has splitted availability on selected date")
    @SqlSetup
    fun `test create should create booking when employee has splitted availability`() {
        Utils.setUserContext(userId = 1)

        val date = Utils.getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "date": "${date}T15:00:00",
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
            jsonPath("$.date").value("${date}T15:00:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(2),
            jsonPath("$.treatmentId").value(2)
        )
    }

    @Test
    @DisplayName("POST /api/bookings - Should  create booking, When selected employee has splitted availability on selected date and bookings")
    @SqlSetup
    fun `test create should create booking when employee has splitted availability and bookings`() {
        Utils.setUserContext(userId = 1)

        val date = Utils.getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "date": "${date}T15:15:00",
                "servicePointId": 1,
                "employeeId": 2,
                "treatmentId": 2
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 15:00:00", 2, 1, 2, 1,"PENDING")

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isCreated).andExpectAll(
            jsonPath("$.id").value(5),
            jsonPath("$.userId").value(1),
            jsonPath("$.date").value("${date}T15:15:00"),
            jsonPath("$.servicePointId").value(1),
            jsonPath("$.employeeId").value(2),
            jsonPath("$.treatmentId").value(2)
        )
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee does not provide treatment")
    @SqlSetup
    fun `test create should return 400 when employee does not provide treatment`() {
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not associated with service point")
    @SqlSetup
    fun `test create should return 400 when employee is not associated with service point`() {
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not available at the specified date")
    @SqlSetup
    fun `test create should return 400 when employee is not available at the specified date`() {
        val date = Utils.getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "date": "${date}T15:15:00",
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not available at specified time slot due to different availability")
    @SqlSetup
    fun `test create should return 400 when employee is not available at specified time slot`() {
        val date = Utils.getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "date": "${date}T14:00:00",
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When employee is not available at specified time slot due bookings")
    @SqlSetup
    fun `test create should return 400 when employee is not available at specified time slot due bookings`() {
        val date = Utils.getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "date": "${date}T15:00:00",
                "servicePointId": 1,
                "employeeId": 1,
                "treatmentId": 1
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 15:00:00", 1, 1, 1, 1, "PENDING")

        mockMvc.perform(
            post("/api/bookings")
                .contentType("application/json")
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 400, When date is in the past")
    fun `test create should return 400 when date is in the past`() {
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
    }

    @Test
    @WithMockUser(username = "testuser", roles = ["USER"])
    @DisplayName("POST /api/bookings - Should return 404, When provided service point not found")
    @SqlSetup
    fun `test create should return 404 when provided service point not found`() {
        val date = Utils.getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "date": "${date}T10:00:00",
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
    }


    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should delete booking, When user is admin")
    @SqlSetup
    fun `test delete should delete booking when user is admin`() {
        Utils.setAdminContext(userId = 3)
        val bookingId = 1

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isOk)

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should return 403, When user is not admin")
    @SqlSetup
    fun `test delete should return 403 when user is not admin`() {
        Utils.setUserContext(userId = 2)
        val bookingId = 1

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isForbidden)
    }

    @Test
    @DisplayName("DELETE /api/bookings/{id} - Should return 404, When booking not found")
    @SqlSetup
    fun `test delete should return 404 when booking not found`() {
        Utils.setAdminContext(userId = 3)
        val bookingId = 100

        mockMvc.perform(delete("/api/bookings/$bookingId"))
            .andExpect(status().isNotFound)
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

    private fun createBookingSql(): String {
        return """
            INSERT INTO booking (id, date, employee_id, service_point_id, treatment_id, user_id, status)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """.trimIndent()
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BookingControllerTest::class.java)
    }
}