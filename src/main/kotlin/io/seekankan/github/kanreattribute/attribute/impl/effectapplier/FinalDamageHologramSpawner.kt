package io.seekankan.github.kanreattribute.attribute.impl.effectapplier

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.effectapplier.JacksonTypedEffectApplier
import io.seekankan.github.kanreattribute.attribute.effectapplier.config.CommonEffectApplierConfig
import io.seekankan.github.kanreattribute.attribute.effectapplier.config.TypedEffectApplierConfig
import io.seekankan.github.kanreattribute.common.effectApplierKeyOf
import io.seekankan.github.kanreattribute.coroutines.Ticks
import io.seekankan.github.kanreattribute.coroutines.tickSeconds
import io.seekankan.github.kanreattribute.extensions.midpoint
import io.seekankan.github.kanreattribute.holograms.HologramManager
import io.seekankan.github.kanreattribute.holograms.hologramConfig
import io.seekankan.github.kanreattribute.message.MessageService
import java.text.DecimalFormat

class FinalDamageHologramSpawner(
    pluginInfo: PluginInfo,
    private val hologramManager: HologramManager,
    private val messageService: MessageService
): JacksonTypedEffectApplier<EntityDamageEventData, FinalDamageHologramSpawnerConfig>(
    pluginInfo,
    effectApplierKeyOf(pluginInfo, "final_damage_hologram_spawner"),
    EntityDamageEventData::class.java,
    FinalDamageHologramSpawnerConfig::class.java
) {
    lateinit var finalDamageDecimalFormatter: DecimalFormat

    override fun loadConfig() {
        super.loadConfig()
        finalDamageDecimalFormatter = DecimalFormat(currentConfig.hologram.formatter)
    }

    override fun createDefaultConfig(): FinalDamageHologramSpawnerConfig {
        return FinalDamageHologramSpawnerConfig()
    }

    override fun applyEffectTyped(
        attributes: AttributeMap,
        eventData: EntityDamageEventData
    ) {
        if(eventData.stage != EntityDamageEventData.HandleStage.HANDLE_ATTACKER) return

        val hologramBaseLocation = eventData.defender.midpoint()
        val parsedDisplayText = messageService.toLegacyText(
            currentConfig.hologram.displayText,
            "final_damage" to finalDamageDecimalFormatter.format(eventData.finalDamage)
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

data class FinalDamageHologramSpawnerConfig(
    override val common: FinalDamageHologramSpawnerCommonConfig = FinalDamageHologramSpawnerCommonConfig(),
    val hologram: FinalDamageHologramSpawnerHologramConfig = FinalDamageHologramSpawnerHologramConfig()
) : TypedEffectApplierConfig()
data class FinalDamageHologramSpawnerCommonConfig(
    override val priority: Int = 100
) : CommonEffectApplierConfig()
data class FinalDamageHologramSpawnerHologramConfig(
    val formatter: String = "#",
    val displayText: String = "<gold>❁ <rainbow><final_damage></rainbow> ❁</gold>",
    val offsetRadius: Double = 0.3,
    val offsetY: Double = 0.3,
    val maxAge: Ticks = 5.tickSeconds
)