package source.code.oneclickbooking.exception

import kotlin.reflect.KClass

class RecordNotFoundException(entityClass: KClass<*>, vararg identifiers: Any) : RuntimeException(
    "${entityClass.simpleName} not found for identifiers: " + identifiers.contentToString()
)