package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.AttributeKeys
import io.seekankan.github.kanreattribute.attribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.Displayable
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.data.KanFlag
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.util.KanRandom

class CritChance(private val plugin: KanReAttribute) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "CritChance")) {

    private val defaultsMap = AttributeKeys.run {
        hashMapOf(
            PRIORITY to 10,
            MIN_VALUE to 0.0,
            MAX_VALUE to 1.0,
            BASE_VALUE to 0.3,
            DISPLAY_NAME to "<blue>暴击概率</blue>",
            FORMATTER to Displayable.DEFAULT_PERCENT_FORMAT_CONFIG
        )
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
                val correctValue = correctValue(attrValue)
                if(KanRandom.chance(correctValue)) {
                    eventData.flagContext.addEnumFlag(KanFlag.CRITICAL)
                }
            }
        }
    }

}