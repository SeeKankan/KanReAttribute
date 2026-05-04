package io.seekankan.github.kanreattribute.attribute.impl.subattribute.defence

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.data.KanFlag
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.data.EventData
import org.bukkit.entity.HumanEntity
import kotlin.math.pow

class Defense(private val plugin: KanReAttribute) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "Defense")) {

    val divisor: Double
        get() = configuration.getDouble("divisor", 100.0)
    val exponent: Double
        get() = configuration.getDouble("exponent", 1.0)


    private val defaultsMap = attributeConfig {
        priority = 10
        baseValue = 0.0
        displayName = "<green>防御数值</green>"
        divisor = 100.0
        exponent = 1.0
    }

    override fun getDefaults(): Map<String, Any> {
        return defaultsMap
    }

    override fun calculateEventNumber(
        attrValue: Double,
        otherAttributes: AttributeMap,
        eventData: EventData
    ) {
        if(eventData !is EntityDamageEventData) return
        if(eventData.stage != EntityDamageEventData.HandleStage.HANDLE_DEFENSE) return
        if(eventData.attacker !is HumanEntity) return

        val correctValue = correctValue(attrValue)
        val poweredDefense = correctValue.pow(exponent)
        val damageMultiplier = divisor / (divisor + poweredDefense)
        val finalDamage = eventData.damage * damageMultiplier
        eventData.damage = finalDamage
    }

}