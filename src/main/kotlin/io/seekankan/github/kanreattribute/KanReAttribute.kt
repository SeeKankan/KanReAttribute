package io.seekankan.github.kanreattribute

import io.seekankan.github.kanreattribute.jackson.JacksonUtil
import io.seekankan.github.kanreattribute.logging.BukkitSLF4JProvider
import net.axay.kspigot.main.KSpigot
import org.bukkit.plugin.Plugin
import org.koin.mp.KoinPlatformTools
import org.slf4j.LoggerFactory

interface KanReAttribute: Plugin {

}
class KanReAttributePlugin: KSpigot(), KanReAttribute {
    companion object {
        const val DO_PRINT_MODULE_DEPEND_TREE: Boolean = true
    }

    private lateinit var pluginModuleManager: PluginModuleManager


    override fun load() {
        BukkitSLF4JProvider.plugin = this
        val logger = LoggerFactory.getLogger(javaClass)
        logger.info("SLF4J successfully bridged to plugin.getLogger()!")

        pluginModuleManager = PluginModuleManager(this)
        prepare()
    }

    override fun startup() {
        pluginModuleManager.enable()

        if(DO_PRINT_MODULE_DEPEND_TREE) {
            val koin = KoinPlatformTools.defaultContext().get()
        }
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