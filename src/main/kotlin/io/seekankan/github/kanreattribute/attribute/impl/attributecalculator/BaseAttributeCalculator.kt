package io.seekankan.github.kanreattribute.attribute.impl.attributecalculator

import io.seekankan.github.kanreattribute.attribute.AttributeCalculator
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import org.bukkit.entity.LivingEntity

class BaseAttributeCalculator(
    private val attributeManager: AttributeManager
): AttributeCalculator {
    override val priority = 0

    override fun calculate(
        entity: LivingEntity,
        lastAttributeValues: AttributeMap
    ) {
        attributeManager.subAttributeRegistry.pipeLineView.forEach { subAttribute ->
            lastAttributeValues.add(subAttribute.uniqueName, subAttribute.baseValue)
        }
    }

    override val uniqueName = "BaseAttributeCalculator"
}