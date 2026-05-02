package io.seekankan.github.kanreattribute.item.manager

import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.item.registry.ItemConditionRegistry
import org.bukkit.entity.LivingEntity
import org.koin.core.component.KoinComponent
import java.util.logging.Logger

class ItemConditionManager(
    private val logger: Logger
): KoinComponent {
    val itemConditionRegistry = ItemConditionRegistry(logger)

    fun filterInventoryItems(livingEntity: LivingEntity, invData: LivingEntityInventoryData) {
        itemConditionRegistry.forEach { itemCondition ->
            itemCondition.filterInvalidItems(livingEntity, invData)
        }
    }
}