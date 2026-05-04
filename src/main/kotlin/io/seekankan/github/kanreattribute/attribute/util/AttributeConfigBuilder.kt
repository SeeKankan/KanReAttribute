package io.seekankan.github.kanreattribute.attribute.util

import io.seekankan.github.kanreattribute.attribute.Displayable
import io.seekankan.github.kanreattribute.attribute.AttributeKeys
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.defence.HealthScaleMode

data class AttributeConfigBuilder(
    var priority: Int? = null,
    var minValue: Double = 0.0,
    var maxValue: Double = Double.MAX_VALUE,
    var baseValue: Double = 0.0,
    var divisor: Double? = null,
    var exponent: Double? = null,
    var scaleHealth: Double? = null,
    var scaleMode: HealthScaleMode? = null,
    var displayName: String? = null,
    var formatter: String = Displayable.DEFAULT_NUMBER_FORMAT_CONFIG,
) {
    fun build(): Map<String, Any> {
        val map = hashMapOf<String, Any>()
        return AttributeKeys.run {
            map[PRIORITY] = priority ?: throw IllegalArgumentException("Priority must be set")

            map[MIN_VALUE] = minValue
            map[MAX_VALUE] = maxValue
            map[BASE_VALUE] = baseValue

            divisor?.let { map[DIVISOR] = it }
            exponent?.let { map[EXPONENT] = it }

            scaleHealth?.let {
                if(it <= 0) throw IllegalArgumentException("Scale health must be greater than zero")
                map[SCALE_HEALTH] = it
            }
            scaleMode?.let { map[SCALE_MODE] = it.name }

            map[DISPLAY_NAME] = displayName ?: throw IllegalArgumentException("Display Name must be set")
            map[FORMATTER] = formatter

            //Check
            if(maxValue <= minValue) {
                throw IllegalArgumentException("Max Value must be greater than min")
            }
            divisor?.let {
                if(it < 0.0) {
                    throw IllegalArgumentException("Divisor must be non-negative")
                } else if (it < 0.5) {
                    throw IllegalArgumentException("Divisor must be much greater than zero")
                }
            }
            map
        }
    }
}
fun attributeConfig(builder: AttributeConfigBuilder.() -> Unit):  Map<String, Any> {
    return AttributeConfigBuilder().apply(builder).build()
}