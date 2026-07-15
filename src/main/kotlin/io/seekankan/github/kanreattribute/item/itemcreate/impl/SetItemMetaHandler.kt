package io.seekankan.github.kanreattribute.item.itemcreate.impl

import io.seekankan.github.kanreattribute.common.ItemCreateHandlerKey
import io.seekankan.github.kanreattribute.common.itemCreateHandlerKey
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateContext
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateHandler
import io.seekankan.github.kanreattribute.item.itemcreate.ItemMetaAssembler
import io.seekankan.github.kanreattribute.util.gsonDisplayName
import io.seekankan.github.kanreattribute.util.gsonLore
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

class SetItemMetaHandler(
    private val plugin: Plugin,
    private val itemMetaAssembler: ItemMetaAssembler
): ItemCreateHandler {
    override val priority: Int = 0

    override fun handleItemStack(
        itemStack: ItemStack,
        context: ItemCreateContext
    ) {
        val itemType = context.itemType
        val itemDisplayName = itemMetaAssembler.assembleDisplayName(itemType)
        val itemLore = itemMetaAssembler.assembleComponentLore(itemType)


//        val itemDisplayName = itemMetaAssembler.assembleGsonDisplayName(itemType)
//        val itemLore = itemMetaAssembler.assembleGsonLore(itemType)
//
//        itemStack.meta {
//            setDisplayName(itemDisplayName)
//            lore = itemLore
//        }
//
//        itemStack.gsonDisplayName = itemDisplayName
//        itemStack.gsonLore = itemLore

        itemStack.meta {
            displayName(itemDisplayName)
            lore(itemLore)
        }
    }

    override val uniqueName: ItemCreateHandlerKey = itemCreateHandlerKey(plugin, "ItemLoreHandler")
}