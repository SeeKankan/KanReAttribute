package io.seekankan.github.kanreattribute.item.finder.impl

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.common.ItemFinderKey
import io.seekankan.github.kanreattribute.common.itemFinderKey
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import io.seekankan.github.kanreattribute.item.data.ItemWrapper
import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.item.finder.ItemFinder
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import io.seekankan.github.kanreattribute.util.isValid
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

class VanillaInventoryItemFinder(
    private val plugin: KanReAttribute,
    private val itemManager: ItemTypeManager,
): ItemFinder {
    override val priority: Int = 0

    override fun findItem(
        livingEntity: LivingEntity,
        invData: LivingEntityInventoryData
    ) {
        livingEntity.equipment?.run {
            putItem(invData, ItemSlot.MAIN_HAND, itemInMainHand)
            putItem(invData, ItemSlot.OFF_HAND, itemInOffHand)

            putItem(invData, ItemSlot.HEAD, helmet)
            putItem(invData, ItemSlot.CHEST, chestplate)
            putItem(invData, ItemSlot.LEGS, leggings)
            putItem(invData, ItemSlot.FEET, boots)
        }

    }
    private fun putItem(invData: LivingEntityInventoryData,slot: ItemSlot, item: ItemStack?) {
        if(item.isValid()) {
            val itemWrapper = ItemWrapper(item!!, itemManager.getItemType(item))
            invData.putItemBySlot(slot, itemWrapper)
        }
    }

    override val uniqueName: ItemFinderKey = itemFinderKey(plugin, "VanillaInventoryItemFinder")
}