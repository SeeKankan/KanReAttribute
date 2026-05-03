package io.seekankan.github.kanreattribute.message

object ItemStyleKey {
    const val FORMAT_FULL_MATERIAL_LORE = "format_full_material_lore"
    const val FORMAT_FULL_GEAR_LORE = "format_full_gear_lore"

    const val EMPTY_ITEM_INTRODUCTION = "empty_item_introduction"

    const val ITEM_CATEGORY = "item_category"
    const val ITEM_SLOTS = "item_slots"
    const val EACH_ITEM_SLOT_DELIMITER = "each_item_slot_delimiter"

    const val EACH_ITEM_ATTRIBUTE = "each_item_attribute"
    const val ATTRIBUTE_DISPLAY_NAME = "attribute_display_name"
    const val ATTRIBUTE_VALUE = "attribute_value"

    const val ITEM_DISPLAY_NAME = "item_display_name"
    const val ITEM_INTRODUCTION = "item_introduction"
    const val ITEM_ATTRIBUTES = "item_attributes"
}
fun String.wrapTag() = "<$this>"