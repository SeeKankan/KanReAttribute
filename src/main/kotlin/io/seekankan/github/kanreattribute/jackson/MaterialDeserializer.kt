package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.Registry

class MaterialDeserializer: KanDeserializer<Material>(Material::class.java) {
    override fun deserializeValue(
        parser: JsonParser,
        context: DeserializationContext
    ): Material? {
        val keyString = parser.valueAsString ?: return null
        val key = NamespacedKey.fromString(keyString)
        return key?.let { Registry.MATERIAL.get(it) } ?: Material.matchMaterial(keyString) ?: Material.valueOf(keyString)
    }
}