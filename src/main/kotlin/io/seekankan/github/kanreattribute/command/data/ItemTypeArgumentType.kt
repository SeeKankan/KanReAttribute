package io.seekankan.github.kanreattribute.command.data

import io.seekankan.github.kanreattribute.common.ItemTypeTag
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry
import io.seekankan.github.kanreattribute.registry.impl.ItemTypeRegistry
import org.koin.core.component.inject

class ItemTypeArgumentType: RegisterableArgumentType<ItemType, ItemTypeTag>() {

    override val registry: CopyOnWriteRegistry<ItemType, ItemTypeTag> by inject<ItemTypeRegistry>()

}