package io.seekankan.github.kanreattribute.attribute.effectapplier

import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.EffectApplierKey
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.registry.LifeCycle
import io.seekankan.github.kanreattribute.registry.Named
import io.seekankan.github.kanreattribute.util.MathUtil

interface EffectApplier: Named<EffectApplierKey>, Comparable<EffectApplier>, LifeCycle {
    val priority: Int

    fun applyEffect(attributes: AttributeMap, eventData: EventData)

    override fun compareTo(other: EffectApplier): Int {
        return MathUtil.compare(
            this,
            priority,
            other,
            other.priority
        )
    }

    override fun onBeforeRegister() {}
    override fun onEnable() {}
    override fun onDisable() {}
}