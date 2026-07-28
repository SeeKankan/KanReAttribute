package io.seekankan.github.kanreattribute.datacontainer

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.jackson.JacksonUtil

class JacksonKeyedDataType<T, E>(
    override val namespacedKey: NamespacedKeyOf<E>,
    primitiveType: Class<*>,
    complexType: Class<T>,
    typeReference: TypeReference<T>,
    mapper: ObjectMapper
) : JacksonDataType<T>(
    primitiveType, complexType, typeReference, mapper
), KeyedDataType<T, E> {

}

inline fun <reified P, reified C, E> keyedDataTypeOf(
    namespacedKey: NamespacedKeyOf<E>,
    mapper: ObjectMapper = JacksonUtil.yamlMapper
): JacksonKeyedDataType<C, E> {
    return JacksonKeyedDataType(
        namespacedKey,
        P::class.java,
        C::class.java,
        object : TypeReference<C>() {},
        mapper
    )
}