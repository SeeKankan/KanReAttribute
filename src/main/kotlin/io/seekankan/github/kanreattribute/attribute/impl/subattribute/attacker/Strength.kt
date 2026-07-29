package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.data.AttributeView
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.subattribute.AttributeKeys.DIVISOR
import io.seekankan.github.kanreattribute.attribute.subattribute.AttributeKeys.EXPONENT
import io.seekankan.github.kanreattribute.attribute.subattribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.common.subAttributeKeyOf
import io.seekankan.github.kanreattribute.data.EventData
import kotlin.math.pow

class Strength(
    private val plugin: KanReAttribute,
    private val pluginInfo: PluginInfo
) : ConfigurableSubAttribute(plugin,
    subAttributeKeyOf(pluginInfo, "strength")
) {

//    private val defaultsMap = hashMapOf(
//        "priority" to 10,
//        "min_value" to plugin.currentConfig.getDouble("min-value", 0.0),
//        "max_value" to plugin.currentConfig.getDouble("max-value", Double.MAX_VALUE),
//        "base_value" to plugin.currentConfig.getDouble("base-value", 1.0),
//        "divisor" to plugin.currentConfig.getDouble("divisor"),
//    )
    val divisor: Double
        get() = configuration.getDouble(DIVISOR, 100.0)
    val exponent: Double
        get() = configuration.getDouble(EXPONENT, 1.0)

//    private val defaultsMap = AttributeKeys.run {
//        hashMapOf(
//            PRIORITY to 10,
//            MIN_VALUE to 0.0,
//            MAX_VALUE to Double.MAX_VALUE,
//            BASE_VALUE to 1.0,
//            DIVISOR to 100.0,
//            EXPONENT to 1.0,
//            displayName to "<red>力量倍率</red>",
//            FORMATTER to Displayable.DEFAULT_NUMBER_FORMAT_CONFIG,
//        )
//    }

    private val defaultsMap = attributeConfig {
        priority = 10
        baseValue = 0.0
        divisor = 100.0
        exponent = 1.0
        displayName = "<red>力量倍率</red>"
    }

    override fun getDefaults(): Map<String, Any> {
        return defaultsMap
    }

    override fun calculateEventNumber(
        attrValue: Double,
        otherAttributes: AttributeView,
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