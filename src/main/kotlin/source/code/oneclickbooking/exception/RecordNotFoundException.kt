package source.code.oneclickbooking.exception

import source.code.oneclickbooking.helper.ExceptionMessages
import source.code.oneclickbooking.helper.MessageResolver
import kotlin.reflect.KClass

class RecordNotFoundException(
    entityClass: KClass<*>,
    vararg identifiers: Any
) : RuntimeException(
    MessageResolver.getMessage(
        key = ExceptionMessages.RECORD_NOT_FOUND,
        args = arrayOf(
            entityClass.simpleName ?: "ENTITY",
            identifiers.joinToString(", ")
        )
    )
)