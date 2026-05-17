package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.registry.Named
import org.bukkit.entity.LivingEntity

interface SubAttribute: Named<AttributeType>, Comparable<SubAttribute> {

    override fun compareTo(other: SubAttribute): Int {
        val num1 = priority.compareTo(other.priority)
        return if(num1 != 0) num1 else {
            val num2 = uniqueName.namespace.compareTo(uniqueName.namespace)
            if(num2 != 0) num2 else uniqueName.key.compareTo(other.uniqueName.key)
        }
    }

    fun calculateEventNumber(attrValue: Double, otherAttributes: AttributeMap, eventData: EventData)

    fun onUpdate(entity: LivingEntity, attrValue: Double, otherAttributes: AttributeMap) {

    }

    val isPersistent: Boolean
    val priority: Int

    val minValue: Double
        get() = Double.MIN_VALUE
    val maxValue: Double
        get() = Double.MAX_VALUE
    val baseValue: Double
        get() = 0.0

    fun correctValue(attrValue: Double): Double {
        return attrValue.coerceIn(minValue, maxValue)
    }

    fun onBeforeRegister() {}
    fun onEnable() {}
    fun onReload() {}
    fun onDisable() {}
}