package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.item.message.ItemDefinitions
import io.seekankan.github.kanreattribute.message.ItemLoreParser
import io.seekankan.github.kanreattribute.message.ItemStyleKey
import io.seekankan.github.kanreattribute.message.wrapTag

class ItemMetaAssembler(
    private val itemLoreParser: ItemLoreParser,
    private val attributeManager: AttributeManager,
    private val itemDefinitions: ItemDefinitions
) {
    fun assembleGsonLore(itemType: ItemType): List<String> {

        val itemCategoryConfig = itemDefinitions.getCategoryConfig(itemType.category)
        val itemCategory = itemCategoryConfig.displayName
        val usedLoreTemplate = itemCategoryConfig.loreTemplate
        val itemRawLore = itemType.lore.ifEmpty {
            listOf(usedLoreTemplate)
        }

        val itemSlotsList = itemType.slots
        val itemSlotsString = itemDefinitions.getSlotListDisplayName(itemSlotsList).joinToString(separator = ItemStyleKey.EACH_ITEM_SLOT_DELIMITER.wrapTag())

        val itemTypeIntro = if(itemType.introduction != null) {
            itemType.introduction!!
        } else {
            ItemStyleKey.EMPTY_ITEM_INTRODUCTION.wrapTag()
        }
        val itemTypeAttributeMap = itemType.attrMap.toMiniMessageLoreData(attributeManager)
        val itemAttribute = itemLoreParser.parseList(
            ItemStyleKey.EACH_ITEM_ATTRIBUTE.wrapTag(),
            itemTypeAttributeMap
        )

        val injectArgs = arrayOf(
            ItemStyleKey.ITEM_DISPLAY_NAME to itemType.displayName,
            ItemStyleKey.ITEM_CATEGORY to itemCategory,
            ItemStyleKey.ITEM_SLOTS to itemSlotsString,
            ItemStyleKey.ITEM_INTRODUCTION to itemTypeIntro,
            ItemStyleKey.ITEM_ATTRIBUTES to itemAttribute
        )
        val itemLore = itemLoreParser.parseGsonLore(
            itemRawLore,
            *injectArgs
        )
        return itemLore
    }
    fun assembleGsonDisplayName(itemType: ItemType): String {
        val injectArgs = arrayOf(
            ItemStyleKey.ITEM_DISPLAY_NAME to itemType.displayName
        )
        val itemDisplayName = itemLoreParser.parseGsonLore(
            listOf(itemType.displayName),
            *injectArgs
        )
        return itemDisplayName.first()
    }
}