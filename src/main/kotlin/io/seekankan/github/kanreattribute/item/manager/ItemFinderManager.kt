package io.seekankan.github.kanreattribute.item.manager

import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.item.registry.ItemFinderRegistry
import org.bukkit.entity.LivingEntity
import org.koin.core.component.KoinComponent
import java.util.logging.Logger

class ItemFinderManager(
    private val logger: Logger
): KoinComponent {
    val itemFinderRegistry = ItemFinderRegistry(logger)

    fun findInventoryItems(livingEntity: LivingEntity): LivingEntityInventoryData {
        val invData = LivingEntityInventoryData()
        itemFinderRegistry.forEach { itemFinder ->
            itemFinder.findItem(livingEntity, invData)
        }
        return invData
    }
}