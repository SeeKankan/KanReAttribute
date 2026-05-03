package io.seekankan.github.kanreattribute.item.message

import io.seekankan.github.kanreattribute.common.ResourceLocation
import io.seekankan.github.kanreattribute.item.data.ItemCategory
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import io.seekankan.github.kanreattribute.util.ConfigFileHolder
import org.bukkit.plugin.Plugin
import java.io.File

class ItemDefinitions(
    private val plugin: Plugin
): ConfigFileHolder<ItemDefinitionConfig>(
    plugin,
    ItemDefinitionConfig::class.java,
    File(ResourceLocation.TAG_RESOLVER_FOLDER, "item_definitions.yml").path
) {

    fun getSlotDisplayName(itemSlot: ItemSlot): String {
        return config.slots.getOrDefault(itemSlot, itemSlot.name)
    }
    fun getSlotListDisplayName(itemSlots: List<ItemSlot>): List<String> {
        return itemSlots.map {
            getSlotDisplayName(it)
        }
    }
    fun getCategoryConfig(category: ItemCategory): ItemCategoryConfig {
        return config.itemCategories[category] ?: throw IllegalArgumentException("Unknown item category ${category.name}. This category does not config in item_definitions.yml.")
    }
}