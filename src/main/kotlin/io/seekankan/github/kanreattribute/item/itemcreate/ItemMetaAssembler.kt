package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.message.ItemLoreParser
import io.seekankan.github.kanreattribute.message.ItemStyleKey
import io.seekankan.github.kanreattribute.message.wrapTag

class ItemMetaAssembler(
    private val itemLoreParser: ItemLoreParser,
    private val attributeManager: AttributeManager
) {
    fun assembleLegacyTextsLore(itemType: ItemType): List<String> {

        val itemTypeIntro = if(itemType.introduction != null) {
            itemType.introduction!!
        } else {
            ItemStyleKey.EMPTY_ITEM_INTRODUCTION.wrapTag()
        }
        val itemTypeAttributeMap = itemType.attrMap.toMiniMessageLoreData(attributeManager)
        val itemAttribute = itemLoreParser.parseList( //正常的
            ItemStyleKey.EACH_ITEM_ATTRIBUTE.wrapTag(),
            itemTypeAttributeMap
        )

        val injectArgs = arrayOf(
            ItemStyleKey.ITEM_DISPLAY_NAME to itemType.displayName,
            ItemStyleKey.ITEM_INTRODUCTION to itemTypeIntro,
            ItemStyleKey.ITEM_ATTRIBUTES to itemAttribute
        )
        val itemLore = itemLoreParser.parseLore( //问题代码 TODO
            itemType.lore,
            *injectArgs
        )
        return itemLore
    }
    fun assembleLegacyTextDisplayName(itemType: ItemType): String {
        val injectArgs = arrayOf(
            ItemStyleKey.ITEM_DISPLAY_NAME to itemType.displayName
        )
        val itemDisplayName = itemLoreParser.parseLore(
            listOf(itemType.displayName),
            *injectArgs
        )
        return itemDisplayName.first()
    }
}