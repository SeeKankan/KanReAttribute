package io.seekankan.github.kanreattribute.util

import io.seekankan.github.kanreattribute.PluginInfo
import org.bukkit.plugin.Plugin

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
        nullableConfig = saveFileAndReadYAML(pluginInfo, configClass, resourcePath)
    }

}