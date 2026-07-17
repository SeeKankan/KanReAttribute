package io.seekankan.github.kanreattribute.jackson

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.KeyDeserializer
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.SubAttributeKey

class NamespacedKeyOfKeyDeserializer: KeyDeserializer() {
    override fun deserializeKey(
        key: String,
        ctxt: DeserializationContext
    ): SubAttributeKey {
        return NamespacedKeyOf.fromString(key) ?: throw IllegalArgumentException("Illegal NamespacedKey \"$key\"")
    }

}