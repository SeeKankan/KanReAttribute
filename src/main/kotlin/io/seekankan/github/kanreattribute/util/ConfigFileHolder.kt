package io.seekankan.github.kanreattribute.util

import org.bukkit.plugin.Plugin

class ConfigFileHolder<E>(
    val plugin: Plugin,
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
        nullableConfig = saveFileAndReadYAML(plugin, configClass, resourcePath)
    }

}