package io.seekankan.github.kanreattribute.util

import io.seekankan.github.kanreattribute.PluginInfo

open class ConfigFileHolder<E>(
    private val pluginInfo: PluginInfo,
    val configClass: Class<E>,
    val resourcePath: String
) {
    private var nullableConfig: E? = null
    val config: E
        get() {
            if (nullableConfig == null) {
                throw IllegalStateException("Config must be load before use")
            } else return nullableConfig!!
        }
    fun loadConfig() {
        nullableConfig = pluginInfo.loadYAML(configClass, resourcePath)
    }

}