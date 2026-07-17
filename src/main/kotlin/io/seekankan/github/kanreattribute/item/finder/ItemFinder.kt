package io.seekankan.github.kanreattribute.item.finder

import io.seekankan.github.kanreattribute.common.ItemFinderTag
import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.registry.Registerable
import org.bukkit.entity.LivingEntity

interface ItemFinder: Registerable<ItemFinderTag, ItemFinder> {
//    override fun compareTo(other: ItemFinder): Int {
//        return MathUtil.compare(
//            this,
//            priority,
//            other,
//            other.priority
//        )
//    }
//    override fun onEnable() {}
//    override fun onDisable() {}

    fun findItem(livingEntity: LivingEntity, invData: LivingEntityInventoryData)
}