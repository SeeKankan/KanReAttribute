package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.AttributeKeys
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.Displayable
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.data.EventData

class Damage(private val plugin: KanReAttribute) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "Damage")) {

    private val defaultsMap = attributeConfig {
        priority = 0
        baseValue = 5.0
        displayName = "<red>基础伤害</red>"
    }

    override fun getDefaults(): Map<String, Any> {
        return defaultsMap
    }

    override fun calculateEventNumber(
        attrValue: Double,
        otherAttributes: AttributeMap,
        eventData: EventData
    ) {
        if(eventData is EntityDamageEventData) {
            if(eventData.stage == EntityDamageEventData.HandleStage.HANDLE_ATTACKER) {
                eventData.damage += correctValue(attrValue)
            }
        }
    }

}