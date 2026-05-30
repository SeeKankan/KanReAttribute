package io.seekankan.github.kanreattribute.holograms

import io.seekankan.github.kanreattribute.coroutines.Ticks
import io.seekankan.github.kanreattribute.coroutines.ticks
import io.seekankan.github.kanreattribute.util.KanRandom
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

data class HologramConfig(
    val location: Location,
    val displayText: String,
    val maxAge: Int,
    val updateInterval: Ticks = 1.ticks,
    val onCreate: HologramInstance.() -> Unit = {},
    val onUpdate: HologramInstance.() -> Unit = {},
    val onRemove: HologramInstance.() -> Unit = {}
)
class HologramConfigBuilder(
    var location: Location? = null,
    val displayText: String? = null,
    var offsetRadius: Double = 0.0,
    var offsetY: Double = 0.0,
    var maxAge: Int? = null,
    var updateInterval: Ticks = 1.ticks,
    var onCreate: HologramInstance.() -> Unit = {},
    var onUpdate: HologramInstance.() -> Unit = {},
    var onRemove: HologramInstance.() -> Unit = {}
)

fun hologramConfig(initFunc: HologramConfigBuilder.() -> Unit): HologramConfig {
    val configBuilder = HologramConfigBuilder()
    configBuilder.initFunc()

    val appliedVector = configBuilder.run{
        val angle = KanRandom.nextDouble() * 2 * Math.PI
        val offsetX = cos(angle) * offsetRadius
        val offsetZ = sin(angle) * offsetRadius
        Vector(
            offsetX,
            KanRandom.nextDouble(-offsetY, offsetY),
            offsetZ
        )
    }
    val finalLocation = configBuilder.location?.add(appliedVector) ?: throw IllegalArgumentException("Location must be not null")
    return configBuilder.run {
        HologramConfig(
            location = finalLocation,
            displayText = displayText ?: throw IllegalArgumentException("DisplayText must be not null"),
            maxAge = maxAge ?: throw IllegalArgumentException("MaxAge must be not null"),
            updateInterval = updateInterval,
            onCreate = onCreate,
            onUpdate = onUpdate,
            onRemove = onRemove
        )
    }
}