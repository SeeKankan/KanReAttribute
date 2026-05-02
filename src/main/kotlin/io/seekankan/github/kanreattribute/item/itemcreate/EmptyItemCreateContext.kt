package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.item.itemtype.ItemType

class EmptyItemCreateContext(
    override val itemType: ItemType,
    override val itemTemplate: ItemInstanceConfig,
    override val amount: Int = 1
): ItemCreateContext()