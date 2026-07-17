package io.seekankan.github.kanreattribute.item.itemcreate

import io.seekankan.github.kanreattribute.common.ItemCreateHandlerKey
import io.seekankan.github.kanreattribute.common.ItemCreateHandlerTag
import io.seekankan.github.kanreattribute.registry.LifeCycle
import io.seekankan.github.kanreattribute.util.MathUtil
import io.seekankan.github.kanreattribute.registry.Named
import io.seekankan.github.kanreattribute.registry.Registerable
import org.bukkit.inventory.ItemStack

interface ItemCreateHandler: Registerable<ItemCreateHandlerTag, ItemCreateHandler> {
//    override fun compareTo(other: ItemCreateHandler): Int {
//        return MathUtil.compare(
//            this,
//            priority,
//            other,
//            other.priority
//        )
//    }

//    override fun onEnable() {}
//    override fun onDisable() {}

    fun handleItemStack(itemStack: ItemStack, context: ItemCreateContext)
}