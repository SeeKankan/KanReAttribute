package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.common.ItemCreateHandlerKey
import io.seekankan.github.kanreattribute.util.LifeCycle
import io.seekankan.github.kanreattribute.util.MathUtil
import io.seekankan.github.kanreattribute.util.Named
import org.bukkit.inventory.ItemStack

interface ItemCreateHandler: Named<ItemCreateHandlerKey>, Comparable<ItemCreateHandler>, LifeCycle {
    val priority: Int

    override fun compareTo(other: ItemCreateHandler): Int {
        return MathUtil.compare(
            this,
            priority,
            other,
            other.priority
        )
    }

    override fun onEnable() {}
    override fun onDisable() {}

    fun handleItemStack(itemStack: ItemStack, context: ItemCreateContext)
}