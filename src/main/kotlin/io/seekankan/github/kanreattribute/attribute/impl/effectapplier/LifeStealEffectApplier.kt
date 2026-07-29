package io.seekankan.github.kanreattribute.attribute.impl.effectapplier

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.data.AttributeView
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.data.KanAttributeFlag
import io.seekankan.github.kanreattribute.attribute.data.LifeStealResult
import io.seekankan.github.kanreattribute.attribute.effectapplier.JacksonTypedEffectApplier
import io.seekankan.github.kanreattribute.attribute.effectapplier.config.CommonEffectApplierConfig
import io.seekankan.github.kanreattribute.attribute.effectapplier.config.TypedEffectApplierConfig
import io.seekankan.github.kanreattribute.common.effectApplierKeyOf
import io.seekankan.github.kanreattribute.coroutines.time.Ticks
import io.seekankan.github.kanreattribute.coroutines.time.tickSeconds
import io.seekankan.github.kanreattribute.extensions.midpoint
import io.seekankan.github.kanreattribute.holograms.HologramManager
import io.seekankan.github.kanreattribute.holograms.hologramConfig
import io.seekankan.github.kanreattribute.message.MessageService
import java.text.DecimalFormat

class LifeStealEffectApplier(
    pluginInfo: PluginInfo,
    private val hologramManager: HologramManager,
    private val messageService: MessageService
): JacksonTypedEffectApplier<EntityDamageEventData, LifeStealEffectApplierConfig>(
    pluginInfo,
    effectApplierKeyOf(pluginInfo, "life_steal_effect_applier"),
    EntityDamageEventData::class.java,
    LifeStealEffectApplierConfig::class.java
) {
    lateinit var lifeStealAmountDecimalFormatter: DecimalFormat

    override fun loadConfig() {
        super.loadConfig()
        lifeStealAmountDecimalFormatter = DecimalFormat(currentConfig.hologram.formatter)
    }

    override fun createDefaultConfig(): LifeStealEffectApplierConfig {
        return LifeStealEffectApplierConfig()
    }

    override fun applyEffectTyped(
        attributes: AttributeView,
        eventData: EntityDamageEventData
    ) {
        if(eventData.stage != EntityDamageEventData.HandleStage.HANDLE_ATTACKER) return

        //apply life steal
        val attacker = eventData.attacker
        val attackerFlagContext = eventData.attackerFlagContext
        val isLifeSteal = attackerFlagContext.hasEnumFlag(KanAttributeFlag.LIFE_STEAL)

        if(!isLifeSteal) return

        val lifeStealResult = attackerFlagContext.getObjectFlag<LifeStealResult>() ?: return
        val lifeStealAmount = lifeStealResult.amount
        attacker.heal(lifeStealAmount)


        //apply hologram
        val hologramBaseLocation = eventData.defender.midpoint()
        val parsedDisplayText = messageService.toLegacyText(
            currentConfig.hologram.displayText,
            "life_steal_amount" to lifeStealAmountDecimalFormatter.format(lifeStealAmount)
        )
        hologramManager.spawnHologram(hologramConfig {
            location = hologramBaseLocation
            displayText = parsedDisplayText
            offsetRadius = currentConfig.hologram.offsetRadius
            offsetY = currentConfig.hologram.offsetY
            maxAge = currentConfig.hologram.maxAge
        })
    }

}

data class LifeStealEffectApplierConfig(
    override val common: LifeStealEffectApplierCommonConfig = LifeStealEffectApplierCommonConfig(),
    val hologram: LifeStealHologramConfig = LifeStealHologramConfig()
) : TypedEffectApplierConfig()
data class LifeStealEffectApplierCommonConfig(
    override val priority: Int = 35
) : CommonEffectApplierConfig()
data class LifeStealHologramConfig(
    val formatter: String = "#",
    val displayText: String = "<red>吸血: <rainbow><life_steal_amount></rainbow> </red>",
    val offsetRadius: Double = 0.3,
    val offsetY: Double = 0.3,
    val maxAge: Ticks = 5.tickSeconds
)