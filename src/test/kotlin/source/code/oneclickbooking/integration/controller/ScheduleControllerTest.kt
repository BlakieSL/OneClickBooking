package source.code.oneclickbooking.integration.controller

import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.Utils.createBookingSql
import source.code.oneclickbooking.integration.Utils.getClosestDateForDay
import source.code.oneclickbooking.integration.annotation.SqlSetup
import source.code.oneclickbooking.integration.controller.booking.BookingControllerDeleteTest
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    value = [
        "classpath:testcontainers/schema/drop-schema.sql",
        "classpath:testcontainers/schema/create-schema.sql",
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class ScheduleControllerTest {
    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return schedule, When request with employee")
    @SqlSetup
    fun `test get schedule with employee`() {
        val date = getClosestDateForDay(DayOfWeek.MONDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "EMPLOYEE",
                            "value": 1,
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        val expectedFirstSlot = "${date}T09:00:00"
        val expectedLastSlot = "${date}T17:45:00"
        val expectedSlotCount = calculateExpectedSlotCount("09:00", "18:00")

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.freeSlots").isArray,
            jsonPath("$.freeSlots.length()").value(expectedSlotCount),
            jsonPath("$.freeSlots[0]").value(expectedFirstSlot),
            jsonPath("$.freeSlots[-1]").value(expectedLastSlot)
        )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return schedule, When request with employee " +
            "that has bookings")
    @SqlSetup
    fun `test get schedule with employee that has bookings`() {
        val date = getClosestDateForDay(DayOfWeek.MONDAY)
        val request = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "EMPLOYEE",
                            "value": 1,
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        jdbcTemplate.update(createBookingSql(), 4, "$date 15:00:00", 1, 1, 1, 1,"PENDING")

        val expectedFirstSlot = "${date}T09:00:00"
        val expectedLastSlot = "${date}T17:45:00"
        val expectedSlotCount = calculateExpectedSlotCount("09:00", "18:00") - 1

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.freeSlots").isArray,
            jsonPath("$.freeSlots.length()").value(expectedSlotCount),
            jsonPath("$.freeSlots[0]").value(expectedFirstSlot),
            jsonPath("$.freeSlots[-1]").value(expectedLastSlot),
            jsonPath("$.freeSlots").value(
                Matchers.not(Matchers.containsString("${date}T15:00:00"))
            )
        )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return empty schedule, When employee provided and " +
            "no availabilities found")
    @SqlSetup
    fun `test get schedule with employee and no availabilities`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "EMPLOYEE",
                            "value": 1,
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.freeSlots").isArray,
            jsonPath("$.freeSlots").isEmpty
        )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return empty schedule, When employee not provided and " +
            "no availabilities found")
    @SqlSetup
    fun `test get schedule without employee and no availabilities`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.freeSlots").isArray,
            jsonPath("$.freeSlots").isEmpty
        )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return schedule, When request without employee")
    @SqlSetup
    fun `test get schedule without employee`() {
        val date = getClosestDateForDay(DayOfWeek.THURSDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 1
            }
        """.trimIndent()

        val expectedFirstSlot = "${date}T09:00:00"
        val expectedLastSlot = "${date}T17:45:00"
        val expectedSlotCount = calculateExpectedSlotCount("09:00", "18:00")

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isOk).andExpectAll(
            jsonPath("$.freeSlots").isArray,
            jsonPath("$.freeSlots.length()").value(expectedSlotCount),
            jsonPath("$.freeSlots[0]").value(expectedFirstSlot),
            jsonPath("$.freeSlots[-1]").value(expectedLastSlot)
        )
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When treatment not provided")
    @SqlSetup
    fun `test get schedule without treatment`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                }
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When service point not provided")
    @SqlSetup
    fun `test get schedule without service point`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When date not provided")
    @SqlSetup
    fun `test get schedule without date`() {
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid key provided")
    @SqlSetup
    fun `test get schedule with invalid key`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "INVALID",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid operation provided for date")
    @SqlSetup
    fun `test get schedule with invalid operation for date`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "INVALID"
                        }
                        
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid operation provided " +
            "for employee")
    @SqlSetup
    fun `test get schedule with invalid operation for employee`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "EMPLOYEE",
                            "value": 1,
                            "operation": "INVALID"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid operation provided" +
            " for service point")
    @SqlSetup
    fun `test get schedule with invalid operation for service point`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "INVALID"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isBadRequest)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 404, When treatment not found")
    @SqlSetup
    fun `test get schedule with treatment not found`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 999
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 404, When service point not found")
    @SqlSetup
    fun `test get schedule with service point not found`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 999,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isNotFound)
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 404, When employee was provided and not found")
    @SqlSetup
    fun `test get schedule with employee not found`() {
        val date = getClosestDateForDay(DayOfWeek.SUNDAY)
        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "SERVICE_POINT",
                            "value": 1,
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "DATE",
                            "value": "$date",
                            "operation": "EQUAL"
                        },
                        {
                            "filterKey": "EMPLOYEE",
                            "value": 999,
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 2
            }
        """.trimIndent()

        mockMvc.perform(
            post("/api/schedule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andExpect(status().isNotFound)
    }

    private fun calculateExpectedSlotCount(startTime: String, endTime: String): Int {
        val start = LocalTime.parse(startTime)
        val end = LocalTime.parse(endTime)
        return (Duration.between(start, end).toMinutes() / 15).toInt()
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BookingControllerDeleteTest::class.java)
    }
}