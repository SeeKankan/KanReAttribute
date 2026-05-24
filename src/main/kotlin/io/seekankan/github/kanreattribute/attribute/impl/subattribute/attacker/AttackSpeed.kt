package io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.util.KanRandom
import io.seekankan.github.kanreattribute.util.divAndPow
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.LivingEntity

class AttackSpeed(private val plugin: KanReAttribute) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "AttackSpeed")) {
    private val uuid = KanRandom.generateUUIDFromSeed(
        "${plugin.name}.living_entity.attribute.generic.attack_speed"
    )

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
        if(eventData !is EntityDamageEventData) return
        if(eventData.stage != EntityDamageEventData.HandleStage.HANDLE_ATTACKER) return
        if(eventData.attacker !is HumanEntity) return
        val attackCooldown = eventData.attacker.attackCooldown
        eventData.damage *= attackCooldown
    }

    private fun createAttackSpeedModifier(amount: Double): AttributeModifier {
        return AttributeModifier(
            uuid,
            "${plugin.name}.subattribute.attackspeed",
            amount,
            AttributeModifier.Operation.ADD_NUMBER
        )
    }
    override fun onUpdate(entity: LivingEntity, attrValue: Double, otherAttributes: AttributeMap) {
        val attackSpeedAttribute = entity.getAttribute(Attribute.GENERIC_ATTACK_SPEED)?: return
        val modifierValue = correctValue(attrValue).divAndPow(divisor, exponent)
        val modifier = createAttackSpeedModifier(modifierValue)
        attackSpeedAttribute.removeModifier(modifier)
//        if(modifier in attackSpeedAttribute.modifiers) {
//            attackSpeedAttribute.removeModifier(modifier)
//        }
        attackSpeedAttribute.addModifier(modifier)
    }

}