package io.seekankan.github.kanreattribute

import org.bukkit.plugin.Plugin
import java.io.File
import java.util.logging.Logger

class PluginInfoImpl(
    private val plugin: Plugin
): PluginInfo() {
    override val name: String = plugin.name
    override val version: String = plugin.description.version
    override val dataFolder: File = plugin.dataFolder

    override val logger: Logger = plugin.logger

    override fun saveResource(resourcePath: String, replace: Boolean) {
        plugin.saveResource(resourcePath, replace)
    }
}