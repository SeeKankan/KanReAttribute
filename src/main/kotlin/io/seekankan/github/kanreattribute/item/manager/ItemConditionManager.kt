package io.seekankan.github.kanreattribute.item.manager

import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.registry.impl.ItemConditionRegistry
import org.bukkit.entity.LivingEntity
import org.koin.core.component.KoinComponent
import java.util.logging.Logger

class ItemConditionManager(
    private val logger: Logger,
    val itemConditionRegistry: ItemConditionRegistry
): KoinComponent {


    fun filterInventoryItems(livingEntity: LivingEntity, invData: LivingEntityInventoryData) {
        itemConditionRegistry.snapshot.pipeline.forEach { itemCondition ->
            itemCondition.filterInvalidItems(livingEntity, invData)
        }
    }
}