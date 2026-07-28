package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization

class ConfigurationSerializer: KanSerializer<ConfigurationSerializable>(
    ConfigurationSerializable::class.java
) {
    override fun serializeValue(
        value: ConfigurationSerializable,
        generator: JsonGenerator,
        provider: SerializerProvider
    ) {
        val rawMap = value.serialize().toMutableMap()
        rawMap[ConfigurationSerialization.SERIALIZED_TYPE_KEY] = ConfigurationSerialization.getAlias(value.javaClass)

        generator.writeObject(rawMap)
    }
}