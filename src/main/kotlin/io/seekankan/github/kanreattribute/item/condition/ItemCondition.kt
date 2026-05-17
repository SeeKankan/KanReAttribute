package io.seekankan.github.kanreattribute.item.condition

import io.seekankan.github.kanreattribute.common.ItemConditionKey
import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.registry.LifeCycle
import io.seekankan.github.kanreattribute.util.MathUtil
import io.seekankan.github.kanreattribute.registry.Named
import org.bukkit.entity.LivingEntity

interface ItemCondition: Named<ItemConditionKey>, Comparable<ItemCondition>, LifeCycle {
    val priority: Int

    override fun compareTo(other: ItemCondition): Int {
        return MathUtil.compare(
            this,
            priority,
            other,
            other.priority
        )
    }

    override fun onEnable() {}
    override fun onDisable() {}
    fun filterInvalidItems(livingEntity: LivingEntity, invData: LivingEntityInventoryData)
}