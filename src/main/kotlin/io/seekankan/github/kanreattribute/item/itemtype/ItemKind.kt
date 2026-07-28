package io.seekankan.github.kanreattribute.item.itemtype

import io.seekankan.github.kanreattribute.common.ItemKindDataTag
import io.seekankan.github.kanreattribute.common.ItemTypeTag
import io.seekankan.github.kanreattribute.datacontainer.ReadableDataHolder
import io.seekankan.github.kanreattribute.registry.Registerable

interface ItemKind: Registerable<ItemTypeTag, ItemKind>, ReadableDataHolder<ItemKindDataTag> {

//    fun <T : Any> getData(itemKindDataKey: ItemKindDataKey, type: DataType<T>): T?

}