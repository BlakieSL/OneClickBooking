package source.code.oneclickbooking.service.implementation

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch
import org.springframework.stereotype.Service
import source.code.oneclickbooking.service.declaration.JsonPatchService

@Service
class JsonPatchServiceImpl (private val objectMapper: ObjectMapper): JsonPatchService {
    override fun <T> applyPatch(patch: JsonMergePatch, targetBean: Any, beanClass: Class<T>): T {
        val targetNode: JsonNode = objectMapper.valueToTree(targetBean);
        val patchedNode: JsonNode = patch.apply(targetNode);
        return objectMapper.treeToValue(patchedNode, beanClass);
    }
}