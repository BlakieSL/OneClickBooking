package source.code.oneclickbooking.service.declaration

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import kotlin.reflect.KClass

interface JsonPatchService {
    fun <T : Any> applyPatch(patch: JsonMergePatch, targetBean: Any, beanClass: KClass<T>) : T
}