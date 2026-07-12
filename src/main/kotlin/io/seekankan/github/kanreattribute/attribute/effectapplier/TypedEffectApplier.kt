package io.seekankan.github.kanreattribute.attribute.effectapplier

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.subattribute.Displayable
import io.seekankan.github.kanreattribute.attribute.subattribute.config.TypedAttributeConfig
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.effectapplier.config.TypedEffectApplierConfig
import io.seekankan.github.kanreattribute.common.EffectApplierKey
import io.seekankan.github.kanreattribute.common.key
import io.seekankan.github.kanreattribute.common.namespace
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.extensions.isInstanceOf
import java.io.File
import java.text.DecimalFormat

abstract class TypedEffectApplier<T: EventData, E: TypedEffectApplierConfig>(
    protected val pluginInfo: PluginInfo,
    override val uniqueName: EffectApplierKey,
    val eventDataType: Class<T>
): EffectApplier {

    open val configFile: File = pluginInfo.dataFolder
        .resolve("effect_applier")
        .resolve(uniqueName.namespace)
        .resolve(uniqueName.key + ".yml")

    lateinit var currentConfig: E

    override val priority: Int
        get() = currentConfig.common.priority

    protected abstract fun createDefaultConfig(): E
    protected abstract fun writeConfig(config: E)
    protected abstract fun fetchConfig(): E

    protected open fun loadConfig() {
//        val parentDir = configFile.parentFile
//        if(parentDir != null && !parentDir.exists()) {
//            val created = parentDir.mkdirs()
//            if(!created) {
//                pluginInfo.logger.warning("Cannot create directory: " + parentDir.absolutePath)
//            }
//        }
//        if(!configFile.exists()) {
//            pluginInfo.logger.info("Create currentConfig file: ${configFile.name}")
//            val defaultConfig = createDefaultConfig()
//            writeConfig(defaultConfig)
//        }
//        currentConfig = fetchConfig()
        currentConfig = pluginInfo.saveAndLoadConfig(
            configFile,
            ::createDefaultConfig,
            ::writeConfig,
            ::fetchConfig
        )
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

    final override fun applyEffect(attributes: AttributeMap, eventData: EventData) {
        if(!(eventData isInstanceOf eventDataType)) return
        eventData as T

        applyEffectTyped(attributes, eventData)
    }
    abstract fun applyEffectTyped(attributes: AttributeMap, eventData: T)
}