package io.seekankan.github.kanreattribute.attribute.impl.attributecalculator

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.attributecalculator.AttributeCalculator
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.AttributeCalculatorTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.attributeCalculatorKeyOf
import io.seekankan.github.kanreattribute.item.ItemService
import org.bukkit.entity.LivingEntity

class ItemAttributeCalculator(
    private val pluginInfo: PluginInfo,
    private val itemService: ItemService,
): AttributeCalculator {
    override val priority: Int = 10
    override val isPersistent: Boolean = true

    override fun calculate(
        entity: LivingEntity,
        lastAttributeValues: AttributeMap
    ) {
        val data = itemService.getValidInventoryData(entity)
        itemService.addInAttributeMap(entity, data, lastAttributeValues)
    }

    override val uniqueName: NamespacedKeyOf<AttributeCalculatorTag> = attributeCalculatorKeyOf(pluginInfo, "item_attribute_calculator")
    override fun onEnable() {

    }

    override fun onDisable() {

    }
}