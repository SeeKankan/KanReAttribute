package io.seekankan.github.kanreattribute.attribute.effectapplier

import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.EffectApplierKey
import io.seekankan.github.kanreattribute.common.EffectApplierTag
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.registry.LifeCycle
import io.seekankan.github.kanreattribute.registry.Named
import io.seekankan.github.kanreattribute.registry.Registerable
import io.seekankan.github.kanreattribute.util.MathUtil

interface EffectApplier: Registerable<EffectApplierTag, EffectApplier> {

    fun applyEffect(attributes: AttributeMap, eventData: EventData)
}