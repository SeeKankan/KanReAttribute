package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

abstract class KanSerializer<T>(
    type: Class<T>
): StdSerializer<T>(type) {

    final override fun serialize(p0: T?, p1: JsonGenerator?, p2: SerializerProvider?) {
        p1!!
        p2!!
        if(p0 == null) {
            p1.writeNull()
            return
        }
        serializeValue(
            p0,
            p1,
            p2
        )
    }

    abstract fun serializeValue(value: T, generator: JsonGenerator, provider: SerializerProvider)
}