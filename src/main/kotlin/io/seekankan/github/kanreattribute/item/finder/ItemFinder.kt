package io.seekankan.github.kanreattribute.item.finder

import io.seekankan.github.kanreattribute.common.ItemFinderKey
import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.util.LifeCycle
import io.seekankan.github.kanreattribute.util.MathUtil
import io.seekankan.github.kanreattribute.util.Named
import org.bukkit.entity.LivingEntity

interface ItemFinder: Named<ItemFinderKey>, Comparable<ItemFinder>, LifeCycle {
    val priority: Int
    override fun compareTo(other: ItemFinder): Int {
        return MathUtil.compare(
            this,
            priority,
            other,
            other.priority
        )
    }
    override fun onEnable() {}
    override fun onDisable() {}

    fun findItem(livingEntity: LivingEntity, invData: LivingEntityInventoryData)
}