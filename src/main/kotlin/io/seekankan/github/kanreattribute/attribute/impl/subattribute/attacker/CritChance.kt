package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.subattribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.subattribute.Displayable
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.data.KanAttributeFlag
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.common.subAttributeKeyOf
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.util.KanRandom

class CritChance(
    private val plugin: KanReAttribute,
    private val pluginInfo: PluginInfo,
) : ConfigurableSubAttribute(plugin,
    subAttributeKeyOf(pluginInfo, "crit_chance")
) {

//    private val defaultsMap = AttributeKeys.run {
//        hashMapOf(
//            PRIORITY to 20,
//            MIN_VALUE to 0.0,
//            MAX_VALUE to 1.0,
//            BASE_VALUE to 0.3,
//            DISPLAY_NAME to "<blue>暴击概率</blue>",
//            FORMATTER to Displayable.DEFAULT_PERCENT_FORMAT_CONFIG
//        )
//    }

    private val defaultsMap = attributeConfig {
        priority = 20
        maxValue = 1.0
        baseValue = 0.3
        displayName = "<blue>暴击倍率</blue>"
        formatter = Displayable.DEFAULT_PERCENT_FORMAT_CONFIG
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
                    eventData.attackerFlagContext.addEnumFlag(KanAttributeFlag.CRITICAL)
                }
            }
        }
    }

}