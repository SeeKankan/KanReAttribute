package io.seekankan.github.kanreattribute.item.itemcreate.impl

import io.seekankan.github.kanreattribute.common.ItemCreateHandlerKey
import io.seekankan.github.kanreattribute.common.itemCreateHandlerKey
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateContext
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateHandler
import io.seekankan.github.kanreattribute.item.itemcreate.ItemMetaAssembler
import io.seekankan.github.kanreattribute.util.gsonDisplayName
import io.seekankan.github.kanreattribute.util.gsonLore
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

//        val itemTypeIntro = if(itemType.introduction != null) {
//            itemType.introduction!!
//        } else {
//            ItemStyleKey.EMPTY_ITEM_INTRODUCTION.wrapTag()
//        }
//        val itemTypeAttributeMap = itemType.attrMap.toMiniMessageLoreData(attributeManager)
//        val itemAttribute = itemLoreParser.parseList(
//            ItemStyleKey.EACH_ITEM_ATTRIBUTE.wrapTag(),
//            itemTypeAttributeMap
//        )
//
//        val injectArgs = arrayOf(
//            ItemStyleKey.ITEM_DISPLAY_NAME to itemType.displayName,
//            ItemStyleKey.ITEM_INTRODUCTION to itemTypeIntro,
//            ItemStyleKey.ITEM_ATTRIBUTES to itemAttribute
//        )
//        val itemLore = itemLoreParser.parseGsonLore(
//            itemType.lore,
//            *injectArgs
//        )
        val itemDisplayName = itemMetaAssembler.assembleGsonDisplayName(itemType)
        val itemLore = itemMetaAssembler.assembleGsonLore(itemType)
        itemStack.meta {
            setDisplayName(itemDisplayName)
            lore = itemLore
        }
        itemStack.gsonDisplayName = itemDisplayName
        itemStack.gsonLore = itemLore
    }

    override val uniqueName: ItemCreateHandlerKey = itemCreateHandlerKey(plugin, "ItemLoreHandler")
}