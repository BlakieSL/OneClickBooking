package source.code.oneclickbooking.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class RefreshTokenRequestDto (
    @JsonProperty(required = true)
    @field:NotNull
    val refreshToken: String
)