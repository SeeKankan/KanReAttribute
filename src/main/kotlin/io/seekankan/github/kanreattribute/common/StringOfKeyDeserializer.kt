package io.seekankan.github.kanreattribute.common

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer

class StringOfKeyDeserializer: KeyDeserializer() {
    override fun deserializeKey(
        key: String,
        ctxt: DeserializationContext
    ): Any {
        return StringOf.create<Any>(key)
    }
}