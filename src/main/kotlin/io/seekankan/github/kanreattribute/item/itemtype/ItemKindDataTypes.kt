package io.seekankan.github.kanreattribute.item.itemtype

import io.seekankan.github.kanreattribute.common.PLUGIN_NAMESPACE
import io.seekankan.github.kanreattribute.common.itemKindDataKey
import io.seekankan.github.kanreattribute.datacontainer.keyedDataTypeOf
import io.seekankan.github.kanreattribute.item.data.ItemCategory
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import org.bukkit.Material

object ItemKindDataTypes {

    val DISPLAY_NAME = keyedDataTypeOf<String, String, _>(
        itemKindDataKey(PLUGIN_NAMESPACE, "display_name")
    )
    val ITEM_MATERIAL = keyedDataTypeOf<String, Material, _>(
        itemKindDataKey(PLUGIN_NAMESPACE, "item_material")
    )
    val ITEM_CATEGORY = keyedDataTypeOf<String, ItemCategory, _>(
        itemKindDataKey(PLUGIN_NAMESPACE, "item_category")
    )
    val SLOTS = keyedDataTypeOf<String, ItemSlot, _>(
        itemKindDataKey(PLUGIN_NAMESPACE, "slots")
    )
    val ATTRIBUTES = keyedDataTypeOf<String, ItemSlot, _>(
        itemKindDataKey(PLUGIN_NAMESPACE, "attributes")
    )
    val INTRODUCTION = keyedDataTypeOf<String?, String?, _>(
        itemKindDataKey(PLUGIN_NAMESPACE, "display_name")
    )
    val LORE = keyedDataTypeOf<List<String>, List<String>, _>(
        itemKindDataKey(PLUGIN_NAMESPACE, "lore")
    )

}