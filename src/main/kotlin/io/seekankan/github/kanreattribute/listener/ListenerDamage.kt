package io.seekankan.github.kanreattribute.listener

import io.seekankan.github.kanreattribute.KanDamageEvent
import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.di.AutoRegistrable
import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListenerDamage(private val plugin: KanReAttribute): Listener, AutoRegistrable, KoinComponent {
    private val attributeManager: AttributeManager by inject()
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if(event.entity !is LivingEntity) return
        val defenseEntity = event.entity as LivingEntity
        val attackerEntity = when (event.damager) {
            is Projectile if (event.damager as Projectile).shooter is LivingEntity -> {
                (event.damager as Projectile).shooter as LivingEntity
            }

            is LivingEntity -> {
                event.damager as LivingEntity
            }

            else -> {
                return
            }
        }
//        val defenseEntityAttrs = plugin.attributeManager.getLivingEntityAttribute(defenseEntity)
//        val attackerEntityAttrs = plugin.attributeManager.getLivingEntityAttribute(attackerEntity)
        val defenseEntityAttrs = attributeManager.getLivingEntityAttribute(defenseEntity)
        val attackerEntityAttrs = attributeManager.getLivingEntityAttribute(attackerEntity)


        val damageEventData = EntityDamageEventData(
            event,
            attackerEntity,
            defenseEntity,
            attackerEntityAttrs,
            defenseEntityAttrs,
        )
        damageEventData.useStage(EntityDamageEventData.HandleStage.HANDLE_ATTACKER) {
            attributeManager.handleEventData(attackerEntityAttrs, damageEventData)
        }
        damageEventData.useStage(EntityDamageEventData.HandleStage.HANDLE_DEFENSE) {
            attributeManager.handleEventData(defenseEntityAttrs, damageEventData)
        }
//        attackerEntityAttrs.handleEventData(damageEventData)
//        damageEventData.stage = EntityDamageEventData.HandleStage.HANDLE_DEFENSE
//        defenseEntityAttrs.handleEventData(damageEventData)
        damageEventData.stage = EntityDamageEventData.HandleStage.END

        val kanDamageEvent = KanDamageEvent(damageEventData)
        Bukkit.getPluginManager().callEvent(kanDamageEvent)
        damageEventData.commit()
    }
}