package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.data.KanAttributeFlag
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.data.EventData
import kotlin.math.pow

class CritDamage(private val plugin: KanReAttribute) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "CritDamage")) {

    val divisor: Double
        get() = configuration.getDouble("divisor", 100.0)
    val exponent: Double
        get() = configuration.getDouble("exponent", 1.0)

//    private val defaultsMap = AttributeKeys.run {
//        hashMapOf(
//            PRIORITY to 21,
//            MIN_VALUE to 0.0,
//            MAX_VALUE to Double.MAX_VALUE,
//            BASE_VALUE to 1.0,
//            DISPLAY_NAME to "<blue>暴击倍率</blue>",
//            DIVISOR to 100.0,
//            EXPONENT to 1.0,
//            FORMATTER to Displayable.DEFAULT_NUMBER_FORMAT_CONFIG,
//        )
//    }

    private val defaultsMap = attributeConfig {
        priority = 25
        baseValue = 0.0
        displayName = "<blue>暴击倍率</blue>"
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
        if(eventData is EntityDamageEventData) {
            if(eventData.stage == EntityDamageEventData.HandleStage.HANDLE_ATTACKER
                && eventData.flagContext.hasEnumFlag(KanAttributeFlag.CRITICAL)) {
                val correctValue = correctValue(attrValue)
                val normalized = correctValue / divisor
                val power = 1 + normalized.pow(exponent)
                eventData.damage *= power
            }
        }
    }

}