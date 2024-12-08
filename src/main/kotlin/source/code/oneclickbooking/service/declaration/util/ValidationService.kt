package source.code.oneclickbooking.service.declaration.util

interface ValidationService {
    fun <T> validate(dto: T, vararg groups: Class<*>)
}