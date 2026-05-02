package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.item.registry.ItemCreateHandlerRegistry
import org.bukkit.inventory.ItemStack
import java.util.logging.Logger

class ItemFactory(
    private val logger: Logger
) {
    val itemCreateHandlerRegistry = ItemCreateHandlerRegistry(logger)
    fun createItemStack(context: ItemCreateContext): ItemStack {
        val itemStack = ItemStack(context.itemType.material, context.amount)
        itemCreateHandlerRegistry.forEach {
            it.handleItemStack(itemStack, context)
        }
        return itemStack
    }
}