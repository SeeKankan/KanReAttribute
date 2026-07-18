package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.subattribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.common.subAttributeKeyOf
import io.seekankan.github.kanreattribute.data.EventData

class Damage(
    private val plugin: KanReAttribute,
    private val pluginInfo: PluginInfo
) : ConfigurableSubAttribute(plugin,
    subAttributeKeyOf(pluginInfo, "damage")
) {

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