package source.code.oneclickbooking.integration

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import source.code.oneclickbooking.auth.CustomAuthenticationToken
import java.time.DayOfWeek
import java.time.LocalDate

object Utils {
    fun setUserContext(userId: Int) {
        setContext(userId, "ROLE_USER")
    }

    fun setAdminContext(userId: Int) {
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

    fun getClosestDateForDay(desiredDay: DayOfWeek): String {
        val today = LocalDate.now()
        val currentDay = today.dayOfWeek

        val daysToAdd = if (currentDay < desiredDay) {
            desiredDay.value - currentDay.value
        } else {
            7 - (currentDay.value - desiredDay.value)
        }

        val closestDate = today.plusDays(daysToAdd.toLong())
        return closestDate.toString()
    }
}