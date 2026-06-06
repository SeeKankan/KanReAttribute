package io.seekankan.github.kanreattribute.attribute.data

import io.seekankan.github.kanreattribute.data.EventData
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

data class EntityDamageEventData(
    val event: EntityDamageByEntityEvent,

    val attacker: LivingEntity,
    val defender: LivingEntity,

    val attackerAttributeMap: AttributeMap,
    val defenderAttributeMap: AttributeMap,

    val attackerFlagContext: FlagContext = FlagContext(),
    val defenderFlagContext: FlagContext = FlagContext()
): EventData() {
    enum class HandleStage {
        HANDLE_ATTACKER,
        HANDLE_DEFENSE,
        END
    }
    var stage = HandleStage.HANDLE_ATTACKER

//    var damage
//        get() = event.damage
//        set(value) {
//            event.damage = value
//        }
    var damage: Double = event.damage
//    var isCancelled
//        get() = event.isCancelled
//        set(value) {
//            event.isCancelled = value
//        }
    var isCancelled: Boolean = event.isCancelled
    val finalDamage
        get() = event.finalDamage

//    fun addDamage(addDamage: Double) {
//        damage += addDamage
//    }
//    fun subtractDamage(subtractDamage: Double) {
//        damage -= subtractDamage
//    }
//    fun multiplyDamage(factor: Double) {
//        damage *= factor
//    }
//    fun divideDamage(divisor: Double) {
//        damage *= divisor
//    }

    fun useStage(stage: HandleStage, block: () -> Unit) {
        this.stage = stage
        block()
    }
    fun commit() {
        commit(event)
    }
    fun commit(commitEvent: EntityDamageEvent) {
        commitEvent.damage = damage
        commitEvent.isCancelled = isCancelled
    }
}