package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import io.seekankan.github.kanreattribute.common.StringOf

class StringOfKeyDeserializer: KeyDeserializer() {
    override fun deserializeKey(
        key: String,
        ctxt: DeserializationContext
    ): Any {
        return StringOf.create<Any>(key)
    }
}