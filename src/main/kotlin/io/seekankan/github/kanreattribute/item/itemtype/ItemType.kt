package io.seekankan.github.kanreattribute.item.itemtype

import com.fasterxml.jackson.annotation.JsonProperty
import io.seekankan.github.kanreattribute.attribute.data.AttributeView
import io.seekankan.github.kanreattribute.attribute.data.ImmutableAttributeView
import io.seekankan.github.kanreattribute.common.*
import io.seekankan.github.kanreattribute.item.data.ItemCategory
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import io.seekankan.github.kanreattribute.item.itemcreate.ItemInstanceConfig
import io.seekankan.github.kanreattribute.registry.Registerable
import org.bukkit.Material

data class ItemType(
    @field:JsonProperty("unique-name") override val uniqueName: ItemTypeKey,
    @field:JsonProperty("is-persistent") override val isPersistent: Boolean = false,
    @field:JsonProperty("display-name") val displayName: String = uniqueName.key,
    @field:JsonProperty("priority") override val priority: Int = 0,
    @field:JsonProperty("material") val material: Material,
    @field:JsonProperty("category") val category: ItemCategory = ItemCategory.MATERIAL,
    @field:JsonProperty("slots") val slots: List<ItemSlot> = emptyList(),
    @field:JsonProperty("attributes") val attrMap: AttributeView = ImmutableAttributeView(),
    @field:JsonProperty("introduction") val introduction: String? = null,
    @field:JsonProperty("lore") val lore: List<String> = emptyList(),
    @field:JsonProperty("custom-config") val customConfig: Map<String, Any> = mapOf(),
    @field:JsonProperty("instance-config") val instanceConfig: Map<ItemInstanceConfigKey, ItemInstanceConfig> = mapOf(
        itemInstConfigKey("default") to ItemInstanceConfig()
    )
): Registerable<ItemTypeTag, ItemType>{

//    override fun compareTo(other: ItemType)= run {
//        val num2 = uniqueName.namespace.compareTo(uniqueName.namespace)
//        if(num2 != 0) num2 else uniqueName.key.compareTo(other.uniqueName.key)
//    }
}
