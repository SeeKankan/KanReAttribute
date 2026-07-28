package io.seekankan.github.kanreattribute.datacontainer

data class DataCacheValue<T>(
    val type: DataType<T>,
    val value: T?
) {
    val complexType = type.complexType
    fun <T2> castOrNull(anotherComplexType: Class<T2>): DataCacheValue<T2>? {
        if(complexType != anotherComplexType) return null
        val newType = type as DataType<T2>
        val newValue = value as T2
        return DataCacheValue(
            newType,
            newValue
        )
    }
}