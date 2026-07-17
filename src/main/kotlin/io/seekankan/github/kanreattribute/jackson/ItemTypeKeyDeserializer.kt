package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf

class ItemTypeKeyDeserializer: JsonDeserializer<ItemTypeKey>() {
    override fun deserialize(
        p: JsonParser,
        ctx: DeserializationContext
    ): ItemTypeKey {
        val text = p.text
        return NamespacedKeyOf.Companion.fromString(text) ?: throw IllegalArgumentException("Illegal NamespacedKey \"$text\"")
    }

}