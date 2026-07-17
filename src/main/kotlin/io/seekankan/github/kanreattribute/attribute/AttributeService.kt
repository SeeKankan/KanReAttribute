package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.registry.impl.EffectApplierRegistry
import io.seekankan.github.kanreattribute.registry.impl.SubAttributeRegistry

class AttributeService(
    private val subAttributeRegistry: SubAttributeRegistry,
    private val effectApplierRegistry: EffectApplierRegistry
) {
    fun processEventWithAttribute(attrMap: AttributeMap, eventData: EventData) {
        subAttributeRegistry.forEachMap(attrMap) { attrType, subAttribute, attrValue ->
            if(attrValue != null) subAttribute.calculateEventNumber(attrValue, attrMap, eventData)
        }
    }

    fun applyEffect(attrMap: AttributeMap, eventData: EventData) {
        effectApplierRegistry.forEach { effectApplier ->
            effectApplier.applyEffect(attrMap, eventData)
        }
    }
}