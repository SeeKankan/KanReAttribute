package io.seekankan.github.kanreattribute.attribute.impl.subattribute.defence

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.AttributeKeys
import io.seekankan.github.kanreattribute.attribute.ConfigurableSubAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.attribute.util.attributeConfig
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.util.KanRandom
import io.seekankan.github.kanreattribute.util.divAndPow
import io.seekankan.github.kanreattribute.util.enumValueOfOrDefault
import io.seekankan.github.kanreattribute.util.mutableLazy
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import java.util.logging.Logger

class ExtraHealth(
    private val plugin: KanReAttribute,
    private val logger: Logger,
) : ConfigurableSubAttribute(plugin,
    AttributeType(plugin.name, "ExtraHealth")) {
    private val uuid = KanRandom.generateUUIDFromSeed(
        "${plugin.name}.living_entity.attribute.generic.max_health"
    )

    val divisor: Double
        get() = configuration.getDouble(AttributeKeys.DIVISOR, 1.0)
    val exponent: Double
        get() = configuration.getDouble(AttributeKeys.EXPONENT, 1.0)

    val scaleHealth: Double
        get() = configuration.getDouble(AttributeKeys.SCALE_HEALTH, 40.0)
    private val scaleModeString
        get() = configuration.getString(AttributeKeys.SCALE_MODE, HealthScaleMode.CLAMP.name) ?: HealthScaleMode.CLAMP.name
    var scaleMode by mutableLazy {
        enumValueOfOrDefault(scaleModeString.uppercase()) {
            logger.warning("Invalid scale mode: $scaleModeString. Use default mode CLAMP instead.")
            HealthScaleMode.CLAMP
        }
    }

    private val defaultsMap = attributeConfig {
        priority = 0
        baseValue = 0.0
        scaleHealth = 40.0
        scaleMode = HealthScaleMode.CLAMP
        displayName = "<red>生命加成</red>"
        divisor = 1.0
        exponent = 1.0
    }

    override fun getDefaults(): Map<String, Any> {
        return defaultsMap
    }

    override fun initConfig() {
        super.initConfig()
        scaleMode = enumValueOfOrDefault(scaleModeString.uppercase()) {
            logger.warning("Invalid scale mode: $scaleModeString. Use default mode CLAMP instead.")
            HealthScaleMode.CLAMP
        }
    }

    override fun calculateEventNumber(
        attrValue: Double,
        otherAttributes: AttributeMap,
        eventData: EventData
    ) {
    }

    private fun createMaxHealthModifier(amount: Double): AttributeModifier {
        return AttributeModifier(
            uuid,
            "${plugin.name}.subattribute.max_health",
            amount,
            AttributeModifier.Operation.ADD_NUMBER
        )
    }
    override fun onUpdate(entity: LivingEntity, attrValue: Double, otherAttributes: AttributeMap) {
        val maxHealthAttribute = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)?: return
        val modifierValue = correctValue(attrValue).divAndPow(divisor, exponent)
        val modifier = createMaxHealthModifier(modifierValue)
        maxHealthAttribute.removeModifier(modifier)
//        if(modifier in maxHealthAttribute.modifiers) {
//            maxHealthAttribute.removeModifier(modifier)
//        }
        maxHealthAttribute.addModifier(modifier)

        //Check zip health
        if(entity is Player) {
            val maxHealth = maxHealthAttribute.value
            when(scaleMode) {
                HealthScaleMode.FIXED_SCALE -> {
                    entity.healthScale = scaleHealth
                    entity.isHealthScaled = true
                }
                HealthScaleMode.CLAMP -> {
                    if(maxHealth >= scaleHealth) {
                        entity.healthScale = scaleHealth
                        entity.isHealthScaled = true
                    } else {
                        entity.healthScale = scaleHealth
                        entity.isHealthScaled = false
                    }
                }
                HealthScaleMode.NO_SCALE -> {
                    entity.isHealthScaled = false
                }
            }
        }
    }

}
enum class HealthScaleMode {
    FIXED_SCALE,
    CLAMP,
    NO_SCALE
}