package io.seekankan.github.kanreattribute.item.itemtype

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.data.AttributeView
import io.seekankan.github.kanreattribute.attribute.data.ImmutableAttributeView
import io.seekankan.github.kanreattribute.common.ItemKindDataTag
import io.seekankan.github.kanreattribute.common.itemKindDataKey
import io.seekankan.github.kanreattribute.datacontainer.JacksonKeyedDataType
import io.seekankan.github.kanreattribute.datacontainer.JacksonKeyedDataTypeWithDefault
import io.seekankan.github.kanreattribute.datacontainer.keyedDataTypeOf
import io.seekankan.github.kanreattribute.item.data.ItemCategory
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import org.bukkit.Material

class ItemKindDataTypes(
    private val pluginInfo: PluginInfo
) {

    private inline fun <reified P, reified C> withoutDefault(
        key: String
    ): JacksonKeyedDataType<C, ItemKindDataTag> {
        return keyedDataTypeOf<P, C, ItemKindDataTag>(
            itemKindDataKey(pluginInfo, key)
        )
    }

    private inline fun <reified P, reified C : Any> withDefault(
        key: String,
        defaultValue: C
    ): JacksonKeyedDataTypeWithDefault<C, ItemKindDataTag> {
        return keyedDataTypeOf<P, C, ItemKindDataTag>(
            itemKindDataKey(pluginInfo, key),
            defaultValue
        )
    }

    val displayName = withDefault<String, String>("display_name", "<red>未知物品</red>")
    val itemMaterial = withoutDefault<String, Material>("item_material")
    val itemCategory = withDefault<String, ItemCategory>("item_category", ItemCategory.MATERIAL)
    val slots = withDefault<List<String>, List<ItemSlot>>("item_slot", emptyList())
    val attributes = withDefault<Map<String, Any?>, AttributeView>("attributes", ImmutableAttributeView())
    val introduction = withoutDefault<String?, String?>("introduction")
    val lore = withDefault<List<String>, List<String>>("lore", emptyList())

}