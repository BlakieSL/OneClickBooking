package source.code.oneclickbooking.service.implementation

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import source.code.oneclickbooking.dto.other.UserCredentialsDto
import source.code.oneclickbooking.dto.request.UserCreateDto
import source.code.oneclickbooking.dto.request.UserUpdateDto
import source.code.oneclickbooking.dto.response.UserResponseDto
import source.code.oneclickbooking.exception.LocalizedIllegalArgument
import source.code.oneclickbooking.exception.RecordNotFoundException
import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.UserDetailsBuilder
import source.code.oneclickbooking.mapper.UserMapper
import source.code.oneclickbooking.model.RoleName
import source.code.oneclickbooking.model.User
import source.code.oneclickbooking.repository.RoleRepository
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.declaration.UserService
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import source.code.oneclickbooking.service.declaration.util.ValidationService

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val validationService: ValidationService,
    private val jsonPatchService: JsonPatchService,
    private val mapper: UserMapper,
    private val repository: UserRepository,
    private val roleRepository: RoleRepository,
): UserService, UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        val userCredentials = findUserCredentials(
            username
            ?: throw LocalizedIllegalArgument(ExceptionMessages.USERNAME_CANNOT_BE_NULL)
        )
        return UserDetailsBuilder.build(userCredentials)
    }

    @Transactional
    override fun createUser(request: UserCreateDto): UserResponseDto {
        val user = mapper.toEntity(request, hash(request.password))
        setDefaultRole(user)
        val savedUser = repository.save(user)

        return mapper.toResponseDto(savedUser)
    }

    @Transactional
    override fun deleteUser(id: Int) {
        val user = find(id)
        repository.delete(user)
    }

    @Transactional
    override fun updateUser(id: Int, patch: JsonMergePatch): UserResponseDto {
        val user = find(id)
        val patched = applyPatch(patch)

        validationService.validate(patched)
        mapper.update(user, patched)
        updatePasswordIfNeeded(user, patched)
        updateEmailIfNeeded(user, patched)

        val savedUser = repository.save(user)

        return mapper.toResponseDto(savedUser)
    }

    override fun getUser(id: Int): UserResponseDto {
        return find(id).let { mapper.toResponseDto(it) }
    }

    override fun getUserId(email: String): Int {
        return repository.findUserByEmail(email)?.id
            ?: throw RecordNotFoundException(User::class, email)
    }

    private fun applyPatch(patch: JsonMergePatch) : UserUpdateDto {
        return jsonPatchService.applyPatch(patch, UserUpdateDto(), UserUpdateDto::class)
    }

    private fun find(id: Int) : User {
        return repository.findById(id).orElseThrow { RecordNotFoundException(User::class, id) }
    }

    private fun hash(password: String) : String {
        return passwordEncoder.encode(password)
    }

    private fun setDefaultRole(user: User) {
        val role = roleRepository.findByName(RoleName.USER)
            ?: throw RecordNotFoundException(RoleName::class, RoleName.USER)
        user.roles.add(role)
    }

    private fun findUserCredentials(email: String) : UserCredentialsDto {
        val user = repository.findUserByEmail(email)
            ?: throw RecordNotFoundException(User::class, email)
        return mapper.toCredentialsDto(user)
    }

    private fun updatePasswordIfNeeded(user: User, patchedUser: UserUpdateDto) {
        if (isPasswordUpdate(patchedUser)) {
            if (!passwordsMatch(user.password, patchedUser.oldPassword!!)) {
                throw LocalizedIllegalArgument(ExceptionMessages.OLD_PASSWORD_INCORRECT)
            }
            user.password = hash(patchedUser.password!!)
        }
    }

    private fun updateEmailIfNeeded(user: User, patchedUser: UserUpdateDto) {
        if (isEmailUpdate(patchedUser)) {
            if (!passwordsMatch(user.password, patchedUser.oldPassword!!)) {
                throw LocalizedIllegalArgument(ExceptionMessages.OLD_PASSWORD_INCORRECT)
            }
            user.email = patchedUser.email!!
        }
    }

    private fun isPasswordUpdate(patchedUser: UserUpdateDto): Boolean {
        return (patchedUser.password != null) && (patchedUser.oldPassword != null)
    }

    private fun isEmailUpdate(patchedUser: UserUpdateDto): Boolean {
        return (patchedUser.email != null) && (patchedUser.oldPassword != null)
    }

    private fun passwordsMatch(hashedPassword: String, rawPassword: String): Boolean {
        return (passwordEncoder.matches(rawPassword, hashedPassword))
    }
}