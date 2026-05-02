package io.seekankan.github.kanreattribute.item.itemcreate.impl

import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.common.ItemCreateHandlerKey
import io.seekankan.github.kanreattribute.common.itemCreateHandlerKey
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateContext
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateHandler
import io.seekankan.github.kanreattribute.item.itemcreate.ItemMetaAssembler
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import io.seekankan.github.kanreattribute.message.ItemLoreParser
import io.seekankan.github.kanreattribute.message.ItemStyleKey
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.message.wrapTag
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
//        val itemLore = itemLoreParser.parseLore(
//            itemType.lore,
//            *injectArgs
//        )
        val itemLore = itemMetaAssembler.assembleLegacyTextsLore(itemType)
        val itemDisplayName = itemMetaAssembler.assembleLegacyTextDisplayName(itemType)
        itemStack.meta {
            setDisplayName(itemDisplayName)
            lore = itemLore
        }
    }

    override val uniqueName: ItemCreateHandlerKey = itemCreateHandlerKey(plugin, "ItemLoreHandler")
}