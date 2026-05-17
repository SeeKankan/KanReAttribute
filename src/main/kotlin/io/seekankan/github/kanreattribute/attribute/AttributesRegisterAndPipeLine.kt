package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.registry.RegistryPipeLine

class AttributeCalculatorRegistry(val plugin: KanReAttribute): RegistryPipeLine<String, AttributeCalculator>() {
    override fun register(value: AttributeCalculator): Boolean {
        plugin.logger.info("Register AttributeCalculator >>> [${value.uniqueName}]!")
        return try {
            val b = super.register(value)
            value.onEnable()
            b
        } catch (e: Exception) {
            plugin.logger.severe("Register AttributeCalculator >>> [${value.uniqueName} failed! ${e.message}")
            e.printStackTrace()
            false
        }
    }
    override fun unregister(value: AttributeCalculator): Boolean {
        plugin.logger.info("Unregister AttributeCalculator >>> [${value.uniqueName}]!")
        return try {
            value.onDisable()
            super.unregister(value)
        } catch (e: Exception) {
            plugin.logger.severe("Unregister AttributeCalculator >>> [${value.uniqueName}] failed! ${e.message}")
            e.printStackTrace()
            false
        }
    }

    override fun onDupeRegister(value: AttributeCalculator) {
        super.onDupeRegister(value)
        plugin.logger.warning("Same AttributeCalculator Id！An AttributeCalculator(${value.uniqueName}) was duplicate.")
    }
}

class SubAttributeRegistry(val plugin: KanReAttribute): RegistryPipeLine<AttributeType, SubAttribute>() {
    override fun register(value: SubAttribute): Boolean {
        plugin.logger.info("Register SubAttribute >>> [${value.uniqueName}]!")
        return try {
            value.onBeforeRegister()
            val b = super.register(value)
            value.onEnable()
            b
        } catch (e: Exception) {
            plugin.logger.severe("Register SubAttribute >>> [${value.uniqueName}] fail! ${e.message}")
            e.printStackTrace()
            false
        }
    }
    override fun unregister(value: SubAttribute): Boolean {
        plugin.logger.info("Unregister SubAttribute >>> [${value.uniqueName}]!")
        return try {
            value.onDisable()
            super.unregister(value)
        } catch (e: Exception) {
            plugin.logger.severe("Unregister SubAttribute >>> [${value.uniqueName}] failed! ${e.message}")
            e.printStackTrace()
            false
        }
    }

    override fun onDupeRegister(value: SubAttribute) {
        super.onDupeRegister(value)
        plugin.logger.warning("Same SubAttribute Id！A SubAttribute(${value.uniqueName}) was duplicate.")
    }
}