package io.seekankan.github.kanreattribute

import com.fasterxml.jackson.core.type.TypeReference
import io.seekankan.github.kanreattribute.util.Configurable
import io.seekankan.github.kanreattribute.jackson.saveFileAndReadYAML
import org.bukkit.plugin.Plugin
import java.io.File
import java.util.logging.Logger

class PluginInfoImpl(
    private val plugin: Plugin
): PluginInfo() {
    override val name: String = plugin.name
    override val version: String = plugin.pluginMeta.version
    override val dataFolder: File = plugin.dataFolder

    override val logger: Logger = plugin.logger

    override val snakeCaseName: String = "kanreattribute"

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

    override fun <T> saveAndLoadConfig(
        file: File,
        defaultConfigProvider: () -> T,
        configWriter: (T) -> Unit,
        configFetcher: () -> T
    ): T {
        val parentDir = file.parentFile
        if(parentDir != null && !parentDir.exists()) {
            val created = parentDir.mkdirs()
            if(!created) {
                logger.warning("Cannot create directory: " + parentDir.absolutePath)
            }
        }
        if(!file.exists()) {
            logger.info("Create currentConfig file: ${file.name}")
            val defaultConfig = defaultConfigProvider()
            configWriter(defaultConfig)
        }
        return configFetcher()
    }

    override fun <T> saveAndLoadConfig(
        file: File,
        configurable: Configurable<T>
    ): T {
        return saveAndLoadConfig(
            file,
            configurable::createDefaultConfig,
            configurable::writeConfig,
            configurable::fetchConfig
        )
    }


}