package io.seekankan.github.kanreattribute.attribute.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.seekankan.github.kanreattribute.attribute.subattribute.Displayable
import io.seekankan.github.kanreattribute.common.SubAttributeKey
import io.seekankan.github.kanreattribute.jackson.NamespacedKeyOfKeyDeserializer
import io.seekankan.github.kanreattribute.message.ItemStyleKey
import io.seekankan.github.kanreattribute.registry.impl.SubAttributeRegistry

interface AttributeView: Map<SubAttributeKey, Double> {

    companion object {
        @JvmStatic
        @JsonCreator
        fun create(
            @JsonDeserialize(
                keyUsing = NamespacedKeyOfKeyDeserializer::class,
                contentAs = Double::class
            )
            attributes: Map<SubAttributeKey, Double>
        ): AttributeView {
            return ImmutableAttributeView(attributes)
        }
    }

    fun toMiniMessageLoreData(subAttributeRegistry: SubAttributeRegistry): List<Map<String, String>> {
        return this.mapNotNull { (key, value) ->
            val subAttribute = subAttributeRegistry.snapshot.registerableMap[key]
            if(subAttribute == null || subAttribute !is Displayable) { //ignore non-displayable attribute
                null
            } else {
                mapOf(
                    ItemStyleKey.ATTRIBUTE_DISPLAY_NAME to subAttribute.displayName,
                    ItemStyleKey.ATTRIBUTE_VALUE to subAttribute.formatValue(value)
                )
            }

        }

    }

}