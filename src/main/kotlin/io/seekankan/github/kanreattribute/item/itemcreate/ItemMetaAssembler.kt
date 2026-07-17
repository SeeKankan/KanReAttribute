package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.registry.impl.SubAttributeRegistry
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.item.message.ItemDefinitions
import io.seekankan.github.kanreattribute.message.ItemLoreParser
import io.seekankan.github.kanreattribute.message.ItemStyleKey
import io.seekankan.github.kanreattribute.message.wrapTag
import net.kyori.adventure.text.Component

class ItemMetaAssembler(
    private val itemLoreParser: ItemLoreParser,

    private val subAttributeRegistry: SubAttributeRegistry,
    private val attributeManager: AttributeManager,

    private val itemDefinitions: ItemDefinitions
) {

    fun computeRawLore(itemType: ItemType): List<String> {
        val itemCategoryConfig = itemDefinitions.getCategoryConfig(itemType.category)
        val usedLoreTemplate = itemCategoryConfig.loreTemplate
        val itemRawLore = itemType.lore.ifEmpty {
            listOf(usedLoreTemplate)
        }
        return itemRawLore
    }

    fun computeInjectArgs(itemType: ItemType): Array<Pair<String, Any>> {

        val itemCategoryConfig = itemDefinitions.getCategoryConfig(itemType.category)
        val itemCategory = itemCategoryConfig.displayName



        val itemSlotsList = itemType.slots
        val itemSlotsString = itemDefinitions.getSlotListDisplayName(itemSlotsList).joinToString(separator = ItemStyleKey.EACH_ITEM_SLOT_DELIMITER.wrapTag())

        val itemTypeIntro = itemType.introduction ?: ItemStyleKey.EMPTY_ITEM_INTRODUCTION.wrapTag()
        val itemTypeAttributeMap = itemType.attrMap.toMiniMessageLoreData(subAttributeRegistry)
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
        return injectArgs
    }

    @Deprecated("使用assembleComponentLore")
    fun assembleGsonLore(itemType: ItemType): List<String> {
        val itemRawLore = computeRawLore(itemType)
        val injectArgs = computeInjectArgs(itemType)

        val itemLore = itemLoreParser.parseGsonLore(
            itemRawLore,
            *injectArgs
        )
        return itemLore
    }
    @Deprecated("使用assembleDisplayName")
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

    fun assembleComponentLore(itemType: ItemType): List<Component> {
        val itemRawLore = computeRawLore(itemType)
        val injectArgs = computeInjectArgs(itemType)

        val itemLore = itemLoreParser.parseComponentLore(
            itemRawLore,
            *injectArgs
        )
        return itemLore
    }
    fun assembleDisplayName(itemType: ItemType): Component {
        val injectArgs = arrayOf(
            ItemStyleKey.ITEM_DISPLAY_NAME to itemType.displayName
        )
        val itemDisplayName = itemLoreParser.parseComponentLore(
            listOf(itemType.displayName),
            *injectArgs
        )
        return itemDisplayName.first()
    }

}