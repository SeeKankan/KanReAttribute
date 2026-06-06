package io.seekankan.github.kanreattribute.attribute.subattribute.config

abstract class CommonAttributeConfig {
    open val isPersistent: Boolean = true
    abstract val priority: Int

    open val minValue: Double = Double.MIN_VALUE
    open val maxValue: Double = Double.MAX_VALUE
    open val baseValue: Double = 0.0
}