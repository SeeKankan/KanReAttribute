package io.seekankan.github.kanreattribute.attribute.impl.attributecalculator

import io.seekankan.github.kanreattribute.attribute.attributecalculator.AttributeCalculator
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.SubAttributeRegistry
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import org.bukkit.entity.LivingEntity

class BaseAttributeCalculator(
    private val subAttributeRegistry: SubAttributeRegistry
): AttributeCalculator {
    override val priority = 0

    override fun calculate(
        entity: LivingEntity,
        lastAttributeValues: AttributeMap
    ) {
        subAttributeRegistry.pipeLineView.forEach { subAttribute ->
            lastAttributeValues.add(subAttribute.uniqueName, subAttribute.baseValue)
        }
    }

    override val uniqueName = "BaseAttributeCalculator"
}