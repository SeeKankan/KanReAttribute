package io.seekankan.github.kanreattribute.holograms

import io.seekankan.github.kanreattribute.coroutines.CoroutinesManager
import io.seekankan.github.kanreattribute.coroutines.ONE_TICK
import io.seekankan.github.kanreattribute.coroutines.ticks
import kotlinx.coroutines.cancel
import org.bukkit.entity.ArmorStand

class HologramManager(
    val coroutinesManager: CoroutinesManager
) {
    private fun createHoloArmorStand(holoConfig: HologramConfig): ArmorStand {
        val world = holoConfig.location.world ?: throw IllegalArgumentException("World must be not null")
        val armorStand = world.spawn(holoConfig.location, ArmorStand::class.java) { stand ->
            stand.isVisible = false
            stand.isInvulnerable = true
            stand.isCustomNameVisible = true
            stand.customName = holoConfig.displayText
            stand.isCollidable = false
            stand.isSmall = true
            stand.setGravity(false)
            stand.removeWhenFarAway = true
        }
        return armorStand
    }
    fun spawnHologram(holoConfig: HologramConfig): HologramInstance {
        val armorStand = createHoloArmorStand(holoConfig)
        val hologramInstance = HologramInstance(
            holoConfig,
            armorStand
        )
        val hologramJob = coroutinesManager.launchBukkit {
            try {
                hologramInstance.onCreate()
                for(age in 1..holoConfig.maxAge) {
                    if(hologramInstance.isHologramInvalid()) {
                        cancel("The armorStand of hologram is invalid.")
                        return@launchBukkit
                    }

                    hologramInstance.age = age
                    if(age % hologramInstance.hologramConfig.updateInterval.value == 0L) {
                        hologramInstance.onUpdate()
                    }

                    coroutinesManager.delayTicks(ONE_TICK)
                }
            } finally {
                hologramInstance.onRemove()
                hologramInstance.armorStand?.let {
                    if(it.isValid) it.remove()
                }
                hologramInstance.armorStand = null
            }
        }
        hologramInstance.job = hologramJob

        return hologramInstance
    }
}