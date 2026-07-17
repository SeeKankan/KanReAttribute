package io.seekankan.github.kanreattribute.common

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer

class SubAttributeKeyDeserializer: KeyDeserializer() {
    override fun deserializeKey(
        key: String,
        ctxt: DeserializationContext
    ): SubAttributeKey {
        return NamespacedKeyOf.fromString(key) ?: throw IllegalArgumentException("Illegal NamespacedKey \"$key\"")
    }

}