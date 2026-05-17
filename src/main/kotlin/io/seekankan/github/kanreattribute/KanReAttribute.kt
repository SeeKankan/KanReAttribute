package io.seekankan.github.kanreattribute

import io.seekankan.github.kanreattribute.util.JacksonUtil
import net.axay.kspigot.main.KSpigot
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

interface KanReAttribute: Plugin {

}
class KanReAttributePlugin: KSpigot(), KanReAttribute {

    private lateinit var pluginModuleManager: PluginModuleManager


    override fun load() {
        pluginModuleManager = PluginModuleManager(this)
        prepare()
//        nullableInstance = this
    }

    override fun startup() {
//        nullableInstance = this
        pluginModuleManager.enable()

//        loadReloadable()

//        _attributeManager = AttributeManager(this)
//        registerAttributes()
//
//        mainCommand = MainCommand(this)
//        mainCommand!!.setupCommand(this)
    }

    override fun shutdown() {
        pluginModuleManager.shutdown()
//        _attributeManager = null

//        nullableInstance = null
    }

//    fun loadReloadable() {
//        Message.loadMessage(this)
//        saveDefaultConfig()
//        Config.loadConfig(this)
//    }

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
