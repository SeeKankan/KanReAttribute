package io.seekankan.github.kanreattribute.item.data

import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import org.bukkit.inventory.ItemStack

data class ItemWrapper(
    val itemStack: ItemStack,
    val itemType: ItemType?
)
