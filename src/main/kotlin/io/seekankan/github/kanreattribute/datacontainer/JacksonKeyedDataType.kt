package io.seekankan.github.kanreattribute.datacontainer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.jackson.JacksonUtil

class JacksonKeyedDataType<T, E>(
    override val namespacedKey: NamespacedKeyOf<E>,
    primitiveTypeReference: TypeReference<*>,
    complexType: Class<T>,
    typeReference: TypeReference<T>,
    mapper: ObjectMapper
) : JacksonDataType<T>(
    primitiveTypeReference, complexType, typeReference, mapper
), KeyedDataType<T, E> {

}
class JacksonKeyedDataTypeWithDefault<T: Any, E>(
    override val namespacedKey: NamespacedKeyOf<E>,
    val primitiveTypeReference: TypeReference<*>,
    override val complexType: Class<T>,
    val typeReference: TypeReference<T?>,
    val defaultValue: T,
    val mapper: ObjectMapper
) : DataType<T>, KeyedDataType<T, E> {

    override fun toComplex(primitive: Any?): T {
        return mapper.convertValue(primitive, typeReference) ?: defaultValue
    }

    override fun toPrimitive(value: T): Any? {
        return mapper.convertValue(value, primitiveTypeReference)
    }
}

inline fun <reified P, reified C, E> keyedDataTypeOf(
    namespacedKey: NamespacedKeyOf<E>,
    mapper: ObjectMapper = JacksonUtil.yamlMapper
): JacksonKeyedDataType<C, E> {
    return JacksonKeyedDataType(
        namespacedKey,
        object : TypeReference<P>() {},
        C::class.java,
        object : TypeReference<C>() {},
        mapper
    )
}

inline fun <reified P, reified C: Any, E> keyedDataTypeOf(
    namespacedKey: NamespacedKeyOf<E>,
    defaultValue: C,
    mapper: ObjectMapper = JacksonUtil.yamlMapper
): JacksonKeyedDataTypeWithDefault<C, E> {
    return JacksonKeyedDataTypeWithDefault(
        namespacedKey,
        object : TypeReference<P>() {},
        C::class.java,
        object : TypeReference<C?>() {},
        defaultValue,
        mapper
    )
}