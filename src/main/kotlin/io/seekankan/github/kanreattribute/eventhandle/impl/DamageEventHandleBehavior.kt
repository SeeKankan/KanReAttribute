package io.seekankan.github.kanreattribute.eventhandle.impl

import io.seekankan.github.kanreattribute.attribute.AttributeService
import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.common.EventHandleBehaviorKey
import io.seekankan.github.kanreattribute.common.eventHandleBehaviorKeyOf
import io.seekankan.github.kanreattribute.eventhandle.EventHandleBehavior

class DamageEventHandleBehavior(
    pluginInfo: PluginInfo,
    private val attributeManager: AttributeManager,
    private val attributeService: AttributeService
): EventHandleBehavior<EntityDamageEventData> {
    override val priority: Int = 10
    override val targetEventDataClass: Class<in EntityDamageEventData> = EntityDamageEventData::class.java

    override fun handleEventData(eventData: EntityDamageEventData) {
        val attackerEntityAttrs = eventData.attackerAttributeMap
        val defenseEntityAttrs = eventData.defenderAttributeMap

        eventData.useStage(EntityDamageEventData.HandleStage.HANDLE_ATTACKER) {
            attributeService.processEventWithAttribute(attackerEntityAttrs, eventData)
        }
        eventData.useStage(EntityDamageEventData.HandleStage.HANDLE_DEFENSE) {
            attributeService.processEventWithAttribute(defenseEntityAttrs, eventData)
        }
        eventData.stage = EntityDamageEventData.HandleStage.END

        eventData.commit()

        if(eventData.isCancelled) return

        eventData.useStage(EntityDamageEventData.HandleStage.HANDLE_ATTACKER) {
            attributeService.applyEffect(attackerEntityAttrs, eventData)
        }
        eventData.useStage(EntityDamageEventData.HandleStage.HANDLE_DEFENSE) {
            attributeService.applyEffect(defenseEntityAttrs, eventData)
        }
        eventData.stage = EntityDamageEventData.HandleStage.END
    }

    override val uniqueName: EventHandleBehaviorKey = eventHandleBehaviorKeyOf(pluginInfo, "damage_event_handle_behavior")
}