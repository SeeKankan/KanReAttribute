package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.AttributeKeys
import io.seekankan.github.kanreattribute.attribute.AttributeKeys.DIVISOR
import io.seekankan.github.kanreattribute.attribute.AttributeKeys.EXPONENT
import io.seekankan.github.kanreattribute.attribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.Displayable
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.data.EventData
import kotlin.math.pow

class Strength(private val plugin: KanReAttribute) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "Strength")) {

//    private val defaultsMap = hashMapOf(
//        "priority" to 10,
//        "min_value" to plugin.config.getDouble("min-value", 0.0),
//        "max_value" to plugin.config.getDouble("max-value", Double.MAX_VALUE),
//        "base_value" to plugin.config.getDouble("base-value", 1.0),
//        "divisor" to plugin.config.getDouble("divisor"),
//    )
    val divisor: Double
        get() = configuration.getDouble(DIVISOR, 100.0)
    val exponent: Double
        get() = configuration.getDouble(EXPONENT, 1.0)

    private val defaultsMap = AttributeKeys.run {
        hashMapOf(
            PRIORITY to 10,
            MIN_VALUE to 0.0,
            MAX_VALUE to Double.MAX_VALUE,
            BASE_VALUE to 1.0,
            DIVISOR to 100.0,
            EXPONENT to 1.0,
            DISPLAY_NAME to "<red>力量倍率</red>",
            FORMATTER to Displayable.DEFAULT_NUMBER_FORMAT_CONFIG,
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
                val normalized = correctValue / divisor
                val power = 1 + normalized.pow(exponent)
                eventData.damage *= power
            }
        }
    }

}