package io.seekankan.github.kanreattribute.datacontainer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.seekankan.github.kanreattribute.jackson.JacksonUtil

open class JacksonDataType<T>(
    val primitiveType: Class<*>,
    override val complexType: Class<T>,
    val typeReference: TypeReference<T>,
    private val mapper: ObjectMapper
) : DataType<T> {

    override fun toComplex(primitive: Any?): T {
        return mapper.convertValue(primitive, typeReference)
    }

    override fun toPrimitive(value: T): Any? {
        return mapper.convertValue(value, primitiveType)
    }
}
inline fun <reified P, reified C> dataTypeOf(
    mapper: ObjectMapper = JacksonUtil.yamlMapper
): JacksonDataType<C> {
    return JacksonDataType(
        P::class.java,
        C::class.java,
        object : TypeReference<C>() {},
        mapper
    )
}
