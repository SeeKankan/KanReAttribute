package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.databind.module.SimpleModule
import org.bukkit.configuration.serialization.ConfigurationSerializable

class ConfigurationSerializableModule: SimpleModule("ConfigurationSerializableModule") {

    init {

        addSerializer(ConfigurationSerializable::class.java, ConfigurationSerializer())
        addDeserializer(ConfigurationSerializable::class.java, ConfigurationDeserializer())

    }

}