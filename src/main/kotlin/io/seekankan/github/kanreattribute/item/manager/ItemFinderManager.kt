package io.seekankan.github.kanreattribute.item.manager

import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.registry.impl.ItemFinderRegistry
import org.bukkit.entity.LivingEntity
import org.koin.core.component.KoinComponent
import java.util.logging.Logger

class ItemFinderManager(
    private val logger: Logger,
    val itemFinderRegistry: ItemFinderRegistry
): KoinComponent {
//    val itemFinderRegistry = ItemFinderRegistry(logger)

    fun findInventoryItems(livingEntity: LivingEntity): LivingEntityInventoryData {
        val invData = LivingEntityInventoryData()
        itemFinderRegistry.snapshot.pipeline.forEach { itemFinder ->
            itemFinder.findItem(livingEntity, invData)
        }
        return invData
    }
}