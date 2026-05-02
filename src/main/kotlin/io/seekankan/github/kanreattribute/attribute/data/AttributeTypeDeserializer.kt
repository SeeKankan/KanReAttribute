package io.seekankan.github.kanreattribute.attribute.data

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.KeyDeserializer

class AttributeTypeDeserializer: JsonDeserializer<AttributeType>() {
    override fun deserialize(
        p: JsonParser,
        ctx: DeserializationContext
    ): AttributeType {
        val text = p.text
        return AttributeType.create(text)
    }
}
class AttributeTypeKeyDeserializer: KeyDeserializer() {
    override fun deserializeKey(
        key: String,
        ctxt: DeserializationContext
    ): Any {
        return AttributeType.create(key)
    }

}