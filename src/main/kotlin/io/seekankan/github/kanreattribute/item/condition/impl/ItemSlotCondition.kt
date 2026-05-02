package io.seekankan.github.kanreattribute.item.condition.impl

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.common.ItemConditionKey
import io.seekankan.github.kanreattribute.common.itemConditionKey
import io.seekankan.github.kanreattribute.item.condition.ItemCondition
import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import org.bukkit.entity.LivingEntity

class ItemSlotCondition(
    private val plugin: KanReAttribute,
    private val itemTypeManager: ItemTypeManager,
): ItemCondition {
    override val priority: Int = 0

    override fun filterInvalidItems(
        livingEntity: LivingEntity,
        invData: LivingEntityInventoryData
    ) {
        invData.invDataEnumMap.forEach { (slot, stacks) ->
            stacks!!.removeAll { stack ->
                val itemType = stack.itemType ?: return@removeAll false
                slot !in itemType.slots
            }
        }
    }

    override val uniqueName: ItemConditionKey = itemConditionKey(plugin, "ItemSlotCondition")
}