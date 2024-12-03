package source.code.oneclickbooking.dto.other

data class UserCredentialsDto(
    val email: String,
    val password: String,
    val roles: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserCredentialsDto

        if (email != other.email) return false
        if (password != other.password) return false
        if (!roles.contentEquals(other.roles)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + roles.contentHashCode()
        return result
    }
}