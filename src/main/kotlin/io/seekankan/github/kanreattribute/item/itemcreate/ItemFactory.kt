package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.registry.impl.ItemConditionRegistry
import io.seekankan.github.kanreattribute.registry.impl.ItemCreateHandlerRegistry
import org.bukkit.inventory.ItemStack
import java.util.logging.Logger

class ItemFactory(
    private val logger: Logger,
    val itemCreateHandlerRegistry: ItemCreateHandlerRegistry
) {

    fun createItemStack(context: ItemCreateContext): ItemStack {
        val itemStack = ItemStack(context.itemType.material, context.amount)
        itemCreateHandlerRegistry.snapshot.pipeline.forEach {
            it.handleItemStack(itemStack, context)
        }
        return itemStack
    }
}