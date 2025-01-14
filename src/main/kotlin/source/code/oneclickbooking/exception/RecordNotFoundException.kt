package source.code.oneclickbooking.exception

import source.code.oneclickbooking.utils.ExceptionMessages
import kotlin.reflect.KClass

class RecordNotFoundException(
    entityClass: KClass<*>,
    vararg identifiers: Any
) : LocalizedException(
    messageKey = ExceptionMessages.RECORD_NOT_FOUND,
    args = arrayOf(
        entityClass.simpleName!!,
        identifiers.joinToString()
    )
)