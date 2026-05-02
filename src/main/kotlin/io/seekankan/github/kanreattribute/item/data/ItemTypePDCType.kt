package io.seekankan.github.kanreattribute.item.data

import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.common.key
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataType

object ItemTypePDCType: PersistentDataType<String, ItemTypeKey> {
    override fun getPrimitiveType(): Class<String> {
        return String::class.java
    }

    override fun getComplexType(): Class<ItemTypeKey> {
        return ItemTypeKey::class.java
    }

    override fun toPrimitive(
        complex: ItemTypeKey,
        context: PersistentDataAdapterContext
    ): String {
        return complex.toString()
    }

    override fun fromPrimitive(
        primitive: String,
        context: PersistentDataAdapterContext
    ): ItemTypeKey {
        return ItemTypeKey(NamespacedKey.fromString(primitive)!!)
    }

}