package io.seekankan.github.kanreattribute.item.itemcreate

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

//interface ItemInstanceConfig: Named<String>, Map<String, Any> {
//}
class ItemInstanceConfig(
    val map: Map<String, Any> = emptyMap()
): Map<String, Any> by map {
    companion object {
        @JvmStatic
        @JsonCreator
        fun create(
//            @JsonProperty("attributes")
//            @JsonDeserialize(
//                keyUsing = AttributeTypeKeyDeserializer::class,
//                contentAs = Double::class
//            )
            attributes: Map<String, Any>
        ): ItemInstanceConfig {
            return ItemInstanceConfig(attributes)
        }
    }

    @JsonValue
    fun toMap(): Map<String, Any> {
        return map
    }
}