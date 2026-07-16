package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import org.bukkit.Keyed

class KeyedSerializer: KanSerializer<Keyed>(Keyed::class.java) {
    override fun serializeValue(
        value: Keyed,
        generator: JsonGenerator,
        provider: SerializerProvider
    ) {
        generator.writeString(value.key.toString())
    }

}