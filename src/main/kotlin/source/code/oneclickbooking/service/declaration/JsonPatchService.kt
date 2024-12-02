package source.code.oneclickbooking.service.declaration

import com.github.fge.jsonpatch.mergepatch.JsonMergePatch

interface JsonPatchService {
    fun <T> applyPatch(patch: JsonMergePatch, targetBean: Any, beanClass: Class<T>) : T
}