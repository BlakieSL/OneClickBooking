package source.code.oneclickbooking.service.declaration

interface ValidationService {
    fun <T> validate(dto: T, vararg groups: Class<*>)
}