package source.code.oneclickbooking.controller

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import source.code.oneclickbooking.annotation.AccountOwnerOrAdmin
import source.code.oneclickbooking.dto.request.RefreshTokenRequestDto
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.response.AccessTokenResponseDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.service.declaration.UserService
import source.code.oneclickbooking.service.implementation.util.JwtService

@RestController
@RequestMapping("/api/users")
class UserController (
    private val userService: UserService,
    private val jwtService: JwtService
) {
    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) : ResponseEntity<UserResponseDto> =
        ResponseEntity.ok(userService.getUser(id))

    @PostMapping("/register")
    fun create(
        @Valid @RequestBody request: UserCreateDto
    ) : ResponseEntity<UserResponseDto> =
        ResponseEntity.status(201).body(userService.createUser(request))

    @AccountOwnerOrAdmin
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) : ResponseEntity<Unit> =
        userService.deleteUser(id).let { ResponseEntity.noContent().build() }

    @AccountOwnerOrAdmin
    @PatchMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @RequestBody patch: JsonMergePatch
    ) : ResponseEntity<UserResponseDto> =
        ResponseEntity.ok(userService.updateUser(id, patch))

    @PostMapping("/refresh-token")
    fun refreshToken(
        @Valid @RequestBody refreshToken: RefreshTokenRequestDto
    ): ResponseEntity<AccessTokenResponseDto> {
        val newAccessToken = jwtService.refreshAccessToken(refreshToken.refreshToken)
        return ResponseEntity.ok(AccessTokenResponseDto(newAccessToken))
    }
}