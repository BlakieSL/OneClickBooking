package source.code.oneclickbooking.validation.email

import jakarta.persistence.EntityManager
import jakarta.persistence.FlushModeType
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.implementation.util.BeanProvider

class UniqueEmailValidator: ConstraintValidator<UniqueEmailDomain, String> {
    private lateinit var userRepository: UserRepository

    override fun initialize(constraintAnnotation: UniqueEmailDomain?) {
        super.initialize(constraintAnnotation)
        userRepository = BeanProvider.getBean(UserRepository::class.java)
    }

    override fun isValid(email: String?, context: ConstraintValidatorContext?): Boolean {
        if (email.isNullOrEmpty()) return true

        return !userRepository.existsUserByEmail(email)
    }
}