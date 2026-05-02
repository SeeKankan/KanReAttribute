package io.seekankan.github.kanreattribute.util

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.exception.ConfigurationDeserializeException
import org.bukkit.plugin.java.JavaPlugin

object ConfigurationUtil {
     inline fun <reified T> Map<String, Any>.getValueOrThrow(mapKey: String): T {
        val value = this[mapKey] ?: throw ConfigurationDeserializeException("Could not find value for $mapKey")
        val valueTypeName = T::class.java.simpleName
        if(value is Number) {
            if(T::class.java.isAssignableFrom(Number::class.java)) {
                JavaPlugin.getPlugin(KanReAttribute::class.java).logger.warning("You should never use getValueOrThrow function to get number value!")
            }

        }
        return if(value is T) value else throw ConfigurationDeserializeException("Wrong type for $mapKey.Need $valueTypeName but $value.javaClass.simpleName is ${value.javaClass.simpleName}")
    }
     inline fun <reified T: Number> Map<String, Any>.getNumberOrThrow(mapKey: String): T {
        val value = this[mapKey] ?: throw ConfigurationDeserializeException("Could not find value for $mapKey")
        val valueTypeName = T::class.java.simpleName
        if(value is Number) {
            if(T::class.java.isAssignableFrom(Number::class.java)) {
                return MathUtil.convertNumber<T>(value)
            }

        }
        return value as? T
            ?: throw ConfigurationDeserializeException("Wrong type for $mapKey.Need $valueTypeName but $value.javaClass.simpleName is ${value.javaClass.simpleName}")
    }
    fun Map<String, Any>.getBooleanOrThrow(mapKey: String): Boolean {
        val value = this[mapKey] ?: throw ConfigurationDeserializeException("Could not find value for $mapKey")

        return when(value) {
            is Boolean -> value
            is String -> value.toBoolean()
            else -> throw ConfigurationDeserializeException("Wrong type for $mapKey.Need Boolean but $value.javaClass.simpleName is ${value.javaClass.simpleName}")
        }
    }
}