package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.item.itemtype.ItemType

abstract class ItemCreateContext {
    abstract val itemType: ItemType
    abstract val itemTemplate: ItemInstanceConfig
    abstract val amount: Int
}