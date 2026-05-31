package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.Displayable
import io.seekankan.github.kanreattribute.attribute.JacksonTypedSubAttribute
import io.seekankan.github.kanreattribute.attribute.config.CommonAttributeConfig
import io.seekankan.github.kanreattribute.attribute.config.DisplayAttributeConfig
import io.seekankan.github.kanreattribute.attribute.config.NumericConfig
import io.seekankan.github.kanreattribute.attribute.config.TypedAttributeConfig
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.data.KanAttributeFlag
import io.seekankan.github.kanreattribute.util.divAndPow

class LifeSteal(
    pluginInfo: PluginInfo
): JacksonTypedSubAttribute<EntityDamageEventData, LifeStealConfig>(
    pluginInfo,
    AttributeType(pluginInfo.name, "LifeSteal"),
    EntityDamageEventData::class.java,
    LifeStealConfig::class.java

) {
    override fun createDefaultConfig(): LifeStealConfig {
        return LifeStealConfig()
    }

    override fun calculateEventCorrected(
        correctedAttrValue: Double,
        otherAttributes: AttributeMap,
        eventData: EntityDamageEventData
    ) {
        if(!eventData.flagContext.hasEnumFlag(KanAttributeFlag.LIFE_STEAL)) return

        val divisor = currentConfig.numeric.divisor
        val exponent = currentConfig.numeric.exponent
        val lifeStealMultiply = correctedAttrValue.divAndPow(divisor, exponent)
        eventData.damage *= lifeStealMultiply
    }
}

data class LifeStealConfig(
    override val common: LifeStealCommonConfig = LifeStealCommonConfig(),
    override val display: LifeStealDisplayConfig = LifeStealDisplayConfig(),
    val numeric: LifeStealNumericConfig = LifeStealNumericConfig()
) : TypedAttributeConfig()
data class LifeStealCommonConfig(
    override val priority: Int = 35,

    override val minValue: Double = 0.0,
    override val maxValue: Double = Double.MAX_VALUE,
    override val baseValue: Double = 0.0
) : CommonAttributeConfig()
data class LifeStealDisplayConfig(
    override val displayName: String = "<dark_red>吸血倍率</dark_red>",
    override val formatterConfig: String = Displayable.DEFAULT_NUMBER_FORMAT_CONFIG
) : DisplayAttributeConfig()
data class LifeStealNumericConfig(
    override val divisor: Double = 100.0,
    override val exponent: Double = 1.0
): NumericConfig()