package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer

abstract class KanDeserializer<T>(
    type: Class<*>
): StdDeserializer<T>(type) {

    final override fun deserialize(
        p0: JsonParser?,
        p1: DeserializationContext?
    ): T? {
        p0!!
        p1!!
        return deserializeValue(p0, p1)
    }

    abstract fun deserializeValue(parser: JsonParser, context: DeserializationContext): T?

}