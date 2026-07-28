package io.seekankan.github.kanreattribute.datacontainer

import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import org.bukkit.Keyed
import org.bukkit.NamespacedKey


interface KeyedDataType<T, E>: DataType<T>, Keyed {
    val namespacedKey: NamespacedKeyOf<E>

    override fun getKey(): NamespacedKey {
        return namespacedKey.value
    }
}