package io.seekankan.github.kanreattribute.attribute.impl.attributecalculator

import io.seekankan.github.kanreattribute.attribute.AttributeCalculator
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.item.ItemService
import org.bukkit.entity.LivingEntity

class ItemAttributeCalculator(
    private val itemService: ItemService,
): AttributeCalculator {
    override val priority: Int = 10

    override fun calculate(
        entity: LivingEntity,
        lastAttributeValues: AttributeMap
    ) {
        val data = itemService.getValidInventoryData(entity)
        itemService.addInAttributeMap(entity, data, lastAttributeValues)
    }

    override val uniqueName: String = "ItemAttributeCalculator"
}