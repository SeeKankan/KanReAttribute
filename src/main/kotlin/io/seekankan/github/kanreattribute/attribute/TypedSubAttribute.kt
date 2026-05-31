package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.config.TypedAttributeConfig
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeType
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.extensions.isInstanceOf
import java.io.File
import java.text.DecimalFormat

abstract class TypedSubAttribute<T: EventData, E: TypedAttributeConfig>(
    protected val pluginInfo: PluginInfo,
    override val uniqueName: AttributeType,
    val eventDataType: Class<T>
): SubAttribute, Displayable {

    open val configFile: File = pluginInfo.dataFolder
        .resolve("attribute")
        .resolve(uniqueName.namespace)
        .resolve(uniqueName.key + ".yml")

    lateinit var currentConfig: E

    override val isPersistent: Boolean
        get() = currentConfig.common.isPersistent
    override val priority: Int
        get() = currentConfig.common.priority
    override val minValue: Double
        get() = currentConfig.common.minValue
    override val maxValue: Double
        get() = currentConfig.common.maxValue
    override val baseValue: Double
        get() = currentConfig.common.baseValue
    override val displayName: String
        get() = currentConfig.display.displayName
    protected val formatterConfig: String
        get() = currentConfig.display.formatterConfig
    protected lateinit var formatter: DecimalFormat

    protected abstract fun createDefaultConfig(): E
    protected abstract fun writeConfig(config: E)
    protected abstract fun fetchConfig(): E

    protected open fun loadConfig() {
        val parentDir = configFile.parentFile
        if(parentDir != null && !parentDir.exists()) {
            val created = parentDir.mkdirs()
            if(!created) {
                pluginInfo.logger.warning("Cannot create directory: " + parentDir.absolutePath)
            }
        }
        if(!configFile.exists()) {
            pluginInfo.logger.info("Create config file: ${configFile.name}")
            val defaultConfig = createDefaultConfig()
            writeConfig(defaultConfig)
        }
        currentConfig = fetchConfig()

        formatter = DecimalFormat(formatterConfig)
    }

    override fun onBeforeRegister() {
        super.onBeforeRegister()
        loadConfig()
    }
    override fun onEnable() {
        super.onEnable()
//        loadConfig()
    }
    override fun onReload() {
        super.onReload()
        loadConfig()
    }
    override fun formatValue(value: Double): String {
        return formatter.format(value)
    }

    abstract fun calculateEventCorrected(correctedAttrValue: Double, otherAttributes: AttributeMap, eventData: T)
    override fun calculateEventNumber(attrValue: Double, otherAttributes: AttributeMap, eventData: EventData) {
        if(!(eventData isInstanceOf eventDataType)) return
        eventData as T

        calculateEventCorrected(correctValue(attrValue), otherAttributes, eventData)
    }
}