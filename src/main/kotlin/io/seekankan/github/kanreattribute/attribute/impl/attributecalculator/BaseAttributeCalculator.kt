package io.seekankan.github.kanreattribute.attribute.impl.attributecalculator

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.attributecalculator.AttributeCalculator
import io.seekankan.github.kanreattribute.registry.impl.SubAttributeRegistry
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.AttributeCalculatorTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.attributeCalculatorKeyOf
import org.bukkit.entity.LivingEntity

class BaseAttributeCalculator(
    private val pluginInfo: PluginInfo,
    private val subAttributeRegistry: SubAttributeRegistry
): AttributeCalculator {
    override val priority = 0
    override val isPersistent: Boolean = true

    override fun calculate(
        entity: LivingEntity,
        lastAttributeValues: AttributeMap
    ) {
        subAttributeRegistry.snapshot.pipeline.forEach { subAttribute ->
            lastAttributeValues.add(subAttribute.uniqueName, subAttribute.baseValue)
        }
    }

    override val uniqueName: NamespacedKeyOf<AttributeCalculatorTag> = attributeCalculatorKeyOf(pluginInfo, "base_attribute_calculator")
}