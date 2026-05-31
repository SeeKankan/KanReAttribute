package io.seekankan.github.kanreattribute.holograms

import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import org.bukkit.entity.ArmorStand

class HologramInstance(
    val hologramConfig: HologramConfig,
    var armorStand: ArmorStand?
) {
    var job: Job? = null
    var age: Int = 0

    fun isHologramValid(): Boolean {
//        if(armorStand == null) return false
//        if(armorStand!!.isValid) return false
        return armorStand?.isValid ?: false
//        return true
    }
    fun isHologramInvalid(): Boolean {
        return !isHologramValid()
    }

    fun onCreate() {
        hologramConfig.onCreate(this)
    }
    fun onUpdate() {
        hologramConfig.onCreate(this)
    }
    fun onRemove() {
        hologramConfig.onRemove(this)
    }

    fun tick(age: Int) {
        this.age = age
        if(age > 0 && age % hologramConfig.updateInterval.value == 0L) {
            onUpdate()
        }
    }

    fun cancel() {
        job?.cancel("The job was cancelled by hologram.cancel()")
    }

}