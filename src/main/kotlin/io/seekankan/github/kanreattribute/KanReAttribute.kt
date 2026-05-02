package io.seekankan.github.kanreattribute

import io.seekankan.github.kanreattribute.util.EntityDataCache
import io.seekankan.github.kanreattribute.util.JacksonUtil
import net.axay.kspigot.main.KSpigot
import org.bukkit.entity.Entity

class KanReAttribute: KSpigot() {

    lateinit var pluginModuleManager: PluginModuleManager

//    private var _attributeManager: AttributeManager? = null
//    val attributeManager: AttributeManager
//        get() {
//            if (_attributeManager  == null) {
//                throw IllegalStateException("Attribute manager must init before use")
//            } else return _attributeManager!!
//        }
//
//    var mainCommand: MainCommand? = null

    companion object {

//        private var nullableInstance: KanReAttribute? = null


//        @Deprecated("")
//        val instance: KanReAttribute
//            get() {
//                if (nullableInstance  == null) {
//                    throw IllegalStateException("KanReAttribute must be load or enable before use")
//                } else return nullableInstance!!
//            }

    }

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