package source.code.oneclickbooking.validation.email

import jakarta.persistence.EntityManager
import jakarta.persistence.FlushModeType
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import source.code.oneclickbooking.repository.UserRepository
import source.code.oneclickbooking.service.implementation.util.BeanProvider

class UniqueEmailValidator: ConstraintValidator<UniqueEmailDomain, String> {
    private lateinit var userRepository: UserRepository
    private lateinit var entityManager: EntityManager

    override fun initialize(constraintAnnotation: UniqueEmailDomain?) {
        entityManager = BeanProvider.getBean(EntityManager::class.java)
        userRepository = BeanProvider.getBean(UserRepository::class.java)
        super.initialize(constraintAnnotation)
    }

    override fun isValid(email: String?, context: ConstraintValidatorContext?): Boolean {
        if (email.isNullOrEmpty()) return true

        return try {
            entityManager.flushMode = FlushModeType.COMMIT
            !userRepository.existsUserByEmail(email)
        } finally {
            entityManager.flushMode = FlushModeType.AUTO
        }
    }
}