package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.subattribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.helper.PlayerPreAttackCooldownCache
import io.seekankan.github.kanreattribute.util.KanRandom
import io.seekankan.github.kanreattribute.util.divAndPow
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import java.util.logging.Logger

class AttackSpeed(
    private val plugin: KanReAttribute,
    private val logger: Logger,
    private val playerPreAttackCooldownCache: PlayerPreAttackCooldownCache
) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "AttackSpeed")) {
    private val uuid = KanRandom.generateUUIDFromSeed(
        "${plugin.name}.living_entity.attribute.generic.attack_speed"
    )
    private val attributeKey = NamespacedKey(plugin, "subattribute_attack_speed")

    val divisor: Double
        get() = configuration.getDouble("divisor", 1.0)
    val exponent: Double
        get() = configuration.getDouble("exponent", 1.0)

    private val defaultsMap = attributeConfig {
        priority = 1000
        baseValue = 0.0
        displayName = "<yellow>攻击速度</yellow>"
        divisor = 1.0
        exponent = 1.0
    }

    override fun getDefaults(): Map<String, Any> {
        return defaultsMap
    }

    override fun calculateEventNumber(
        attrValue: Double,
        otherAttributes: AttributeMap,
        eventData: EventData
    ) {
        if(eventData is Player) return
        if(eventData !is EntityDamageEventData) return
        if(eventData.stage != EntityDamageEventData.HandleStage.HANDLE_ATTACKER) return
        if(eventData.attacker !is Player) return
        val attackCooldown = playerPreAttackCooldownCache[eventData.attacker.uniqueId] ?: return

        eventData.damage *= attackCooldown
    }

    private fun createAttackSpeedModifier(amount: Double): AttributeModifier {
        return AttributeModifier(
            attributeKey,
            amount,
            AttributeModifier.Operation.ADD_NUMBER
        )
    }
    override fun onUpdate(entity: LivingEntity, attrValue: Double, otherAttributes: AttributeMap) {
        val attackSpeedAttribute = entity.getAttribute(Attribute.ATTACK_SPEED)?: return
        val modifierValue = correctValue(attrValue).divAndPow(divisor, exponent)
        val modifier = createAttackSpeedModifier(modifierValue)
        attackSpeedAttribute.removeModifier(modifier)
//        if(modifier in attackSpeedAttribute.modifiers) {
//            attackSpeedAttribute.removeModifier(modifier)
//        }
        attackSpeedAttribute.addModifier(modifier)
    }

}