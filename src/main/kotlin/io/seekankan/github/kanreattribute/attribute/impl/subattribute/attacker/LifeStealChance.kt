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
import io.seekankan.github.kanreattribute.util.KanRandom
import io.seekankan.github.kanreattribute.util.divAndPow

class LifeStealChance(
    pluginInfo: PluginInfo
): JacksonTypedSubAttribute<EntityDamageEventData, LifeStealChanceConfig>(
    pluginInfo,
    AttributeType(pluginInfo.name, "LifeStealChance"),
    EntityDamageEventData::class.java,
    LifeStealChanceConfig::class.java

) {
    override fun createDefaultConfig(): LifeStealChanceConfig {
        return LifeStealChanceConfig()
    }

    override fun calculateEventCorrected(
        correctedAttrValue: Double,
        otherAttributes: AttributeMap,
        eventData: EntityDamageEventData
    ) {
        val divisor = currentConfig.numeric.divisor
        val exponent = currentConfig.numeric.exponent
        val lifeStealChance = correctedAttrValue.divAndPow(divisor, exponent)

        if(KanRandom.chance(lifeStealChance)) eventData.flagContext.addEnumFlag(KanAttributeFlag.LIFE_STEAL)
    }
}

data class LifeStealChanceConfig(
    override val common: LifeStealChanceCommonConfig = LifeStealChanceCommonConfig(),
    override val display: LifeStealChanceDisplayConfig = LifeStealChanceDisplayConfig(),
    val numeric: LifeStealChanceNumericConfig = LifeStealChanceNumericConfig()
) : TypedAttributeConfig()
data class LifeStealChanceCommonConfig(
    override val priority: Int = 30,

    override val minValue: Double = 0.0,
    override val maxValue: Double = Double.MAX_VALUE,
    override val baseValue: Double = 0.0
) : CommonAttributeConfig()
data class LifeStealChanceDisplayConfig(
    override val displayName: String = "<dark_red>吸血机率</dark_red>",
    override val formatterConfig: String = Displayable.DEFAULT_PERCENT_FORMAT_CONFIG
) : DisplayAttributeConfig()
data class LifeStealChanceNumericConfig(
    override val divisor: Double = 1.0,
    override val exponent: Double = 1.0
): NumericConfig()