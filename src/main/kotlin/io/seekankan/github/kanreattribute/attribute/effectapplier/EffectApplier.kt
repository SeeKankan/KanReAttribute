package io.seekankan.github.kanreattribute.attribute.effectapplier

import io.seekankan.github.kanreattribute.attribute.data.AttributeView
import io.seekankan.github.kanreattribute.common.EffectApplierTag
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.registry.Registerable

interface EffectApplier: Registerable<EffectApplierTag, EffectApplier> {

    fun applyEffect(attributes: AttributeView, eventData: EventData)
}