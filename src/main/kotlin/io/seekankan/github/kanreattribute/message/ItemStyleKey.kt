package io.seekankan.github.kanreattribute.message

object ItemStyleKey {
    const val FORMAT_FULL_ITEM_LORE = "format_full_item_lore"
    const val EMPTY_ITEM_INTRODUCTION = "empty_item_introduction"

    const val EACH_ITEM_ATTRIBUTE = "each_item_attribute"
    const val ATTRIBUTE_DISPLAY_NAME = "attribute_display_name"
    const val ATTRIBUTE_VALUE = "attribute_value"

    const val ITEM_DISPLAY_NAME = "item_display_name"
    const val ITEM_INTRODUCTION = "item_introduction"
    const val ITEM_ATTRIBUTES = "item_attributes"
}
fun String.wrapTag() = "<$this>"