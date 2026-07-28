package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization

class ConfigurationDeserializer: KanDeserializer<ConfigurationSerializable>(
    ConfigurationSerializable::class.java
) {

    companion object {
        private val NULLABLE_POJO_MAP_TYPE_REF = object: TypeReference<Map<String, Any?>?>() {}
    }

    override fun deserializeValue(
        parser: JsonParser,
        context: DeserializationContext
    ): ConfigurationSerializable? {
        val map = parser.codec.readValue(parser, NULLABLE_POJO_MAP_TYPE_REF) ?: return null
        val value = ConfigurationSerialization.deserializeObject(map)
        return value
    }
}