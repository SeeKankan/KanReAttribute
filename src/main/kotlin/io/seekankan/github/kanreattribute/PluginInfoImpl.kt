package io.seekankan.github.kanreattribute

import com.fasterxml.jackson.core.type.TypeReference
import io.seekankan.github.kanreattribute.util.saveFileAndReadYAML
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

    override fun <T> loadYAML(clazz: Class<T>, resourcePath: String): T {
        return saveFileAndReadYAML(this, clazz, resourcePath)
    }

    override fun <T> loadYAML(
        typeRef: TypeReference<T>,
        resourcePath: String
    ): T {
        return saveFileAndReadYAML(this, typeRef, resourcePath)
    }
}