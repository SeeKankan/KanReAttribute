package io.seekankan.github.kanreattribute

import io.seekankan.github.kanreattribute.coroutines.time.Ticks
import io.seekankan.github.kanreattribute.coroutines.time.ticks
import io.seekankan.github.kanreattribute.util.CreateByProviderConfigHolder
import java.io.File

//@Target(AnnotationTarget.FIELD)
//@Retention(AnnotationRetention.RUNTIME)
//annotation class ConfigPath(val value: String)
//
//class Config {
//
//
//    lateinit var currentConfig: FileConfiguration
//    lateinit var defaultValuesCache: MutableMap<Field, Any?>
//
//    @ConfigPath(value = "combat.ban-shield")
//    var banShield: Boolean = false
//    @ConfigPath(value = "combat.clear-minecraft-armor-attribute")
//    var clearMinecraftArmorAttribute: Boolean = true
//
//    @ConfigPath(value = "attribute.debounce-refresh-ticks")
//    var attributeDebounceRefreshDelay:
//
//
//    fun snapshotDefaults() {
//        val clazz = Config::class.java
//
//        if(!this::defaultValuesCache.isInitialized) {
//            defaultValuesCache = HashMap(clazz.declaredFields.size * 2)
//        } else {
//            defaultValuesCache.clear()
//        }
//
//        clazz.declaredFields.forEach { field ->
//            if(field.isAnnotationPresent(ConfigPath::class.java)) {
//                field.isAccessible = true
//                defaultValuesCache[field] = field.get(this)
//            }
//        }
//    }
//
//    fun loadConfig(plugin: KanReAttribute) {
//        currentConfig = plugin.currentConfig
//        val clazz = Config::class.java
//
//        clazz.declaredFields.forEach { field ->
//            if(field.isAnnotationPresent(ConfigPath::class.java)) {
//                field.isAccessible = true
//                val pathAnnotation = field.getAnnotation(ConfigPath::class.java) ?: return@forEach
//
//                val path = pathAnnotation.value
//                val ymlValue = currentConfig.get(path)
//
//                val finalValue = if(ymlValue != null) {
//                    convertType(ymlValue, field)
//                } else {
//                    defaultValuesCache[field]
//                }
//                try {
//                    field.set(this, finalValue)
//                } catch (e: Exception) {
//                    plugin.logger.severe("Set currentConfig field($path) value failed! Except:${field.type.simpleName} Cause: ${e.message}")
//                    e.printStackTrace()
//                }
//            } else return@forEach
//        }
//    }
//
//    fun convertType(value: Any?, field: Field): Any? {
//        if(value == null) return null
//        val fieldType = field.type
//
//        if(value is Map<*,*> && fieldType.isAssignableFrom(Map::class.java)) { //Handle map
//            return value
//        }
//        if(value is List<*> && fieldType.isAssignableFrom(List::class.java)) { //Handle list
//            val elementType = getGenericType(field)
//            val newList = ArrayList<Any?>(value.size)
//            value.forEach { element ->
//                newList.add(convertElement(element, elementType))
//            }
//            return newList
//        }
//        return convertSimpleType(value, fieldType)
//    }
//
//    fun getGenericType(field: Field): Class<*>? {
//        val genericType = field.genericType
//        if(genericType is ParameterizedType) {
//            val actualType =  genericType.actualTypeArguments[0]
//            if(actualType is Class<*>) {
//                return actualType
//            }
//        }
//        val defaultValue = defaultValuesCache[field]
//        if(defaultValue is List<*> && defaultValue.isNotEmpty()) {
//            return defaultValue.firstOrNull()?.javaClass
//        }
//        return null
//    }
//
//    fun convertElement(element: Any?, elementType: Class<*>?): Any? {
//        if(element == null || elementType == null) return null
//        if(elementType.isInstance(element)) return element
//
//        return if(element is Number) {
//            when(elementType) {
//                Byte::class.java -> element.toByte()
//                Short::class.java -> element.toShort()
//                Int::class.java -> element.toInt()
//                Long::class.java -> element.toLong()
//                Float::class.java -> element.toFloat()
//                Double::class.java -> element.toDouble()
//                else -> element
//            }
//        } else {
//            element.toString()
//        }
//    }
//    fun convertSimpleType(value: Any?, elementType: Class<*>?): Any? {
//        if(value == null || elementType == null) return null
//        if(elementType.isInstance(value)) return value
//
//        return when(elementType) {
//            Byte::class.java -> (value as Number).toByte()
//            Short::class.java -> (value as Number).toShort()
//            Int::class.java -> (value as Number).toInt()
//            Long::class.java -> (value as Number).toLong()
//            Float::class.java -> (value as Number) .toFloat()
//            Double::class.java -> (value as Number).toDouble()
//            String::class.java -> value.toString()
//            else -> value
//        }
//    }
//}

class ConfigHolder(
    override val pluginInfo: PluginInfo
): CreateByProviderConfigHolder<Config>() {
    override val configFile: File = pluginInfo.dataFolder
        .resolve("config.yml")
    override val configClass: Class<Config> = Config::class.java

    override fun createDefaultConfig(): Config {
        return Config()
    }

}

data class Config(
    val combat: CombatConfig = CombatConfig(),
    val attribute: AttributeConfig = AttributeConfig()
)
data class CombatConfig(
    val banShield: Boolean = false,
    val clearMinecraftArmorAttribute: Boolean = false
)
data class AttributeConfig(
    val attributeRefreshDelay: Ticks = 5.ticks
)