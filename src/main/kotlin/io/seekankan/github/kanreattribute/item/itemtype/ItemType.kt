package io.seekankan.github.kanreattribute.item.itemtype

import com.fasterxml.jackson.annotation.JsonProperty
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.*
import io.seekankan.github.kanreattribute.item.data.ItemCategory
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import io.seekankan.github.kanreattribute.item.itemcreate.ItemInstanceConfig
import org.bukkit.Material

data class ItemType(
    @param:JsonProperty("unique-name") val uniqueName: ItemTypeKey,
    @param:JsonProperty("display-name") val displayName: String = uniqueName.key,
    @param:JsonProperty("priority") val priority: Int = 0,
    @param:JsonProperty("material") val material: Material,
    @param:JsonProperty("category") val category: ItemCategory = ItemCategory.MATERIAL,
    @param:JsonProperty("slots") val slots: List<ItemSlot> = emptyList(),
    @param:JsonProperty("attributes") val attrMap: AttributeMap = AttributeMap(),
    @param:JsonProperty("introduction") val introduction: String? = null,
    @param:JsonProperty("lore") val lore: List<String> = emptyList(),
    @param:JsonProperty("custom-config") val customConfig: Map<String, Any> = mapOf(),
    @param:JsonProperty("instance-config") val instanceConfig: Map<ItemInstanceConfigKey, ItemInstanceConfig> = mapOf(
        itemInstConfigKey("default") to ItemInstanceConfig()
    )
){

    fun compareTo(other: ItemType)= run {
        val num2 = uniqueName.namespace.compareTo(uniqueName.namespace)
        if(num2 != 0) num2 else uniqueName.key.compareTo(other.uniqueName.key)
    }
}
