package io.seekankan.github.kanreattribute.datacontainer

import io.seekankan.github.kanreattribute.common.NamespacedKeyOf

interface ReadableDataHolder<E> {
    val keys: Set<NamespacedKeyOf<E>>
    val size: Int

    fun <T> getData(key: NamespacedKeyOf<E>, type: DataType<T>): T?
    fun <T> getDatOrThrow(key: NamespacedKeyOf<E>, type: DataType<T>): T {
        return getData(key, type) ?: throw NoSuchElementException("No or unknown value for $key in $this")
    }
    fun <T> getData(keyedType: KeyedDataType<T, E>): T? {
        return getData(keyedType.namespacedKey, keyedType)
    }
    fun <T> getDataOrThrow(keyedType: KeyedDataType<T, E>): T {
        return getData(keyedType) ?: throw NoSuchElementException("No or unknown value for $keyedType in $this")
    }
}