package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.registry.Named
import org.bukkit.entity.LivingEntity

interface AttributeCalculator: Comparable<AttributeCalculator>, Named<String> {
    val priority: Int

    fun calculate(entity: LivingEntity, lastAttributeValues: AttributeMap)

    override fun compareTo(other: AttributeCalculator): Int {
        val num1 = priority.compareTo(other.priority)
        return if(num1 != 0) num1 else uniqueName.compareTo(other.uniqueName)
    }

    fun onEnable() {}
    fun onReload() {}
    fun onDisable() {}

}