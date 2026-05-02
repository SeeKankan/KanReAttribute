package io.seekankan.github.kanreattribute.common

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import org.bukkit.NamespacedKey

class ItemTypeKeyDeserializer: JsonDeserializer<ItemTypeKey>() {
    override fun deserialize(
        p: JsonParser,
        ctx: DeserializationContext
    ): ItemTypeKey {
        val text = p.text
        return NamespacedKeyOf.fromString(text) ?: throw IllegalArgumentException("Illegal NamespacedKey \"$text\"")
    }

}