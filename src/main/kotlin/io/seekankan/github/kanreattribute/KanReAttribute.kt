package io.seekankan.github.kanreattribute

import io.seekankan.github.kanreattribute.util.JacksonUtil
import net.axay.kspigot.main.KSpigot
import org.bukkit.plugin.Plugin

interface KanReAttribute: Plugin {

}
class KanReAttributePlugin: KSpigot(), KanReAttribute {

    private lateinit var pluginModuleManager: PluginModuleManager


    override fun load() {
        pluginModuleManager = PluginModuleManager(this)
        prepare()
    }

    override fun startup() {
        pluginModuleManager.enable()
    }

    override fun shutdown() {
        pluginModuleManager.shutdown()
    }

    private fun prepare() {
        val start = System.currentTimeMillis()
        runCatching {
            logger.info("Preparing utils")
            JacksonUtil.jsonMapper
            JacksonUtil.yamlMapper
        }.onSuccess {
            val end = System.currentTimeMillis()
            logger.info("Prepare utils success(${end - start}ms).")
        }.onFailure {
            logger.severe("Failed to prepare utils")
            it.printStackTrace()
        }

    }


}
