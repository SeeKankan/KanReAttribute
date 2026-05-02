package io.seekankan.github.kanreattribute.item.itemcreate.impl

import io.seekankan.github.kanreattribute.common.ItemCreateHandlerKey
import io.seekankan.github.kanreattribute.common.itemCreateHandlerKey
import io.seekankan.github.kanreattribute.item.data.ItemTypePDCType
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateContext
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateHandler
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import io.seekankan.github.kanreattribute.util.setItemData
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class WriteItemTypeHandler(
    private val plugin: Plugin,
    private val itemTypeManager: ItemTypeManager
): ItemCreateHandler {
    override val priority: Int = 0

    override fun handleItemStack(
        itemStack: ItemStack,
        context: ItemCreateContext
    ) {
        val itemType = context.itemType
        itemStack.setItemData(itemTypeManager.itemTypePDCKey, ItemTypePDCType, itemType.uniqueName)
    }

    override val uniqueName: ItemCreateHandlerKey = itemCreateHandlerKey(plugin, "WriteItemTypeHandler")
}