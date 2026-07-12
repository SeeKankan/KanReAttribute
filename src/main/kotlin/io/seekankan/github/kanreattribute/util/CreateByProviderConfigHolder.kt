package io.seekankan.github.kanreattribute.util

import io.seekankan.github.kanreattribute.PluginInfo
import java.io.File

abstract class CreateByProviderConfigHolder<T>: Configurable<T> {
    protected abstract val pluginInfo: PluginInfo
    protected abstract val configFile: File
    protected abstract val configClass: Class<T>

    private var nullableConfig: T? = null
    val currentConfig: T
        get() {
            if (nullableConfig == null) {
                throw IllegalStateException("Config must be load before use")
            } else return nullableConfig!!
        }

    fun loadConfig() {
        nullableConfig = pluginInfo.saveAndLoadConfig(configFile, this)
    }

    override fun fetchConfig(): T {
        return JacksonUtil.yamlMapper.readValue(configFile, configClass)
    }

    override fun writeConfig(config: T) {
        JacksonUtil.yamlMapper.writeValue(configFile, config)
    }

}