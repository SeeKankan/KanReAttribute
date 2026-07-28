package io.seekankan.github.kanreattribute.datacontainer

interface DataType<T> {
    val complexType: Class<T>
    fun toComplex(primitive: Any?): T
    fun toPrimitive(value: T): Any?

    fun <T2> castUnchecked(anotherComplexType: Class<T2>): DataType<T2> {
        @Suppress("UNCHECKED_CAST")
        return this as DataType<T2>
    }
    fun <T2> castOrNull(anotherComplexType: Class<T2>): DataType<T2>? {
        return if(complexType == anotherComplexType) {
            castUnchecked(anotherComplexType)
        } else null
    }
    fun <T2> castOrThrow(anotherComplexType: Class<T2>): DataType<T2> {
        return if(complexType == anotherComplexType) {
            castUnchecked(anotherComplexType)
        } else throw ClassCastException("Cannot cast ${complexType.simpleName} to ${anotherComplexType.simpleName}")
    }
}