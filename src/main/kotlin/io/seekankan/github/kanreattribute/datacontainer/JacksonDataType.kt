package io.seekankan.github.kanreattribute.datacontainer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.seekankan.github.kanreattribute.jackson.JacksonUtil

open class JacksonDataType<T>(
    val primitiveTypeReference: TypeReference<*>,
    override val complexType: Class<T>,
    val typeReference: TypeReference<T>,
    private val mapper: ObjectMapper
) : DataType<T> {

    override fun toComplex(primitive: Any?): T {
        return mapper.convertValue(primitive, typeReference)
    }

    override fun toPrimitive(value: T): Any? {
        return mapper.convertValue(value, primitiveTypeReference)
    }
}
inline fun <reified P, reified C> dataTypeOf(
    mapper: ObjectMapper = JacksonUtil.yamlMapper
): JacksonDataType<C> {
    return JacksonDataType(
        object : TypeReference<P>() {},
        C::class.java,
        object : TypeReference<C>() {},
        mapper
    )
}
