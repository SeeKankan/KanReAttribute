package io.seekankan.github.kanreattribute.item.data

import org.bukkit.inventory.ItemStack
import java.util.EnumMap

class LivingEntityInventoryData {
    val invDataEnumMap: EnumMap<ItemSlot, MutableList<ItemWrapper>> = EnumMap<ItemSlot, MutableList<ItemWrapper>>(ItemSlot::class.java).apply {
        ItemSlot.entries.forEach { slot ->
            this[slot] = ArrayList(1)
        }
    }

    fun getItemListBySlot(slot: ItemSlot): MutableList<ItemWrapper> {
        return invDataEnumMap[slot]!!
    }
    fun putItemBySlot(slot: ItemSlot, item: ItemWrapper) {
        invDataEnumMap[slot]!!.add(item)
    }
    fun putItemListBySlot(slot: ItemSlot, list: MutableList<ItemWrapper>) {
        invDataEnumMap[slot]!!.addAll(list)
    }
}
