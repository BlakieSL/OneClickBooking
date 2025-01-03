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
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlMergeMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers
import source.code.oneclickbooking.integration.annotation.SqlSetup
import java.time.Duration
import java.time.LocalTime

@ActiveProfiles("test")
@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql(
    value = [
        "classpath:testcontainers/drop-schema.sql",
        "classpath:testcontainers/create-schema.sql",
    ]
)
@AutoConfigureMockMvc
@SpringBootTest
class ScheduleControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    fun setMockMvc(mockMvc: MockMvc) {
        this.mockMvc = mockMvc
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return schedule, When request without employee")
    @SqlSetup
    fun `test get schedule without employee`() {
        logRunning()

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
                            "value": "2025-01-23",
                            "operation": "EQUAL"
                        }
                    ],
                    "dataOption": "AND"
                },
                "treatmentId": 1
            }
        """.trimIndent()

        val expectedFirstSlot = "2025-01-23T09:00:00"
        val expectedLastSlot = "2025-01-23T17:45:00"
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return schedule, When request with employee")
    @SqlSetup
    fun `test get schedule with employee`() {
        logRunning()

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
                            "value": "2025-01-20",
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

        val expectedFirstSlot = "2025-01-20T09:00:00"
        val expectedLastSlot = "2025-01-20T17:45:00"
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return schedule, When request with employee " +
            "that has bookings")
    @SqlSetup
    fun `test get schedule with employee that has bookings`() {
        logRunning()

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
                            "value": "2025-01-06",
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

        val expectedFirstSlot = "2025-01-06T09:00:00"
        val expectedLastSlot = "2025-01-06T17:45:00"
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
                Matchers.not(Matchers.containsString("2025-01-06T15:00:00"))
            )
        )
    }


    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return empty schedule, When employee provided and " +
            "no availabilities found")
    @SqlSetup
    fun `test get schedule with employee and no availabilities`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return empty schedule, When employee not provided and " +
            "no availabilities found")
    @SqlSetup
    fun `test get schedule without employee and no availabilities`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When treatment not provided")
    @SqlSetup
    fun `test get schedule without treatment`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When service point not provided")
    @SqlSetup
    fun `test get schedule without service point`() {
        logRunning()

        val requestBody = """
            {
                "filter": {
                    "filterCriteria": [
                        {
                            "filterKey": "DATE",
                            "value": "2025-01-19",
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
        logRunning()

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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid key provided")
    @SqlSetup
    fun `test get schedule with invalid key`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid operation provided for date")
    @SqlSetup
    fun `test get schedule with invalid operation for date`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid operation provided " +
            "for employee")
    @SqlSetup
    fun `test get schedule with invalid operation for employee`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 400, When invalid operation provided" +
            " for service point")
    @SqlSetup
    fun `test get schedule with invalid operation for service point`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 404, When treatment not found")
    @SqlSetup
    fun `test get schedule with treatment not found`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 404, When service point not found")
    @SqlSetup
    fun `test get schedule with service point not found`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    @DisplayName("POST /api/schedule - Should return 404, When employee was provided and not found")
    @SqlSetup
    fun `test get schedule with employee not found`() {
        logRunning()

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
                            "value": "2025-01-19",
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

        logPassed()
    }

    private fun calculateExpectedSlotCount(startTime: String, endTime: String): Int {
        val start = LocalTime.parse(startTime)
        val end = LocalTime.parse(endTime)
        return (Duration.between(start, end).toMinutes() / 15).toInt()
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