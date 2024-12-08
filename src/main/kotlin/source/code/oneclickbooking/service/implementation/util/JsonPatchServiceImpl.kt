package source.code.oneclickbooking.service.implementation.util

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.springframework.stereotype.Service
import source.code.oneclickbooking.service.declaration.util.JsonPatchService
import kotlin.reflect.KClass

@Service
class JsonPatchServiceImpl (private val objectMapper: ObjectMapper): JsonPatchService {
    override fun <T : Any> applyPatch(patch: JsonMergePatch, targetBean: Any, beanClass: KClass<T>): T {
        val targetNode: JsonNode = objectMapper.valueToTree(targetBean);
        val patchedNode: JsonNode = patch.apply(targetNode);
        return objectMapper.treeToValue(patchedNode, beanClass.java)
    }
}