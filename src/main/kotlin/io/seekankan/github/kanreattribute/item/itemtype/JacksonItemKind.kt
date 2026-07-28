package io.seekankan.github.kanreattribute.item.itemtype

import com.fasterxml.jackson.annotation.JsonIgnore
import io.seekankan.github.kanreattribute.common.ItemKindDataKey
import io.seekankan.github.kanreattribute.common.ItemKindDataTag
import io.seekankan.github.kanreattribute.common.ItemTypeTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.datacontainer.DataType
import io.seekankan.github.kanreattribute.datacontainer.DataCacheValue
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class JacksonItemKind(
    override val priority: Int = 0,
    override val isPersistent: Boolean = false,
    override val uniqueName: NamespacedKeyOf<ItemTypeTag>,
    val data: Map<ItemKindDataKey, Any?>
) : ItemKind {
    @JsonIgnore
    private val dataCacheMap: ConcurrentMap<ItemKindDataKey, DataCacheValue<*>> = ConcurrentHashMap()

    override val keys: Set<NamespacedKeyOf<ItemKindDataTag>> = data.keys
    override val size: Int = data.size

    override fun <T> getData(
        key: ItemKindDataKey,
        type: DataType<T>
    ): T? {
        val cacheValue = dataCacheMap.compute(key) { key, oldValue ->
            if(oldValue != null) {
                if(oldValue.type == type) {
                    return@compute oldValue
                }
            }
            val primitive = data[key]
            val value = primitive?.let {
                type.toComplex(it)
            }
            return@compute DataCacheValue(
                type,
                value
            )
        }
        return cacheValue?.castOrNull(type.complexType)?.value
    }
}