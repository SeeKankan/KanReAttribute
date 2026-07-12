package io.seekankan.github.kanreattribute

import io.seekankan.github.kanreattribute.di.AttributeModule
import io.seekankan.github.kanreattribute.di.BaseConfigModule
import io.seekankan.github.kanreattribute.di.CommandModule
import io.seekankan.github.kanreattribute.di.CoroutineModule
import io.seekankan.github.kanreattribute.di.EventDataHandleModule
import io.seekankan.github.kanreattribute.di.GUIModule
import io.seekankan.github.kanreattribute.di.HologramModule
import io.seekankan.github.kanreattribute.di.ItemModule
import io.seekankan.github.kanreattribute.di.ListenerModule
import io.seekankan.github.kanreattribute.di.PermissionModule
import org.bukkit.event.HandlerList
import org.bukkit.plugin.Plugin
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class PluginModuleManager(
    val plugin: KanReAttribute
) {

    private val pluginModules: List<PluginModule> = listOf(
        BaseConfigModule(plugin),
        CoroutineModule(plugin),

        PermissionModule(plugin),

        HologramModule(plugin),

        ItemModule(plugin),
        AttributeModule(plugin),

        EventDataHandleModule(plugin),

        ListenerModule(plugin),
        GUIModule(plugin),
        CommandModule(plugin)
    )

    fun enable() {
        val moduleList = pluginModules.map { pluginModule ->
            pluginModule.koinModule
        }
//        moduleList.forEach {
//            plugin.logger.info("Module: $it")
//        }
        val pluginInstanceModule = module {
            single<Plugin> { plugin }
//            single<JavaPlugin> { plugin }
            single { plugin }
            single { plugin.logger }

            singleOf(::PluginInfoImpl) bind PluginInfo::class
            single {
                PluginReloader(
                    this@PluginModuleManager::reload
                )
            }
        }
        startKoin {
            modules(pluginInstanceModule)

            modules(moduleList)
        }
        pluginModules.forEach { module ->
            plugin.logger.info("Loading plugin module: ${module.name}")
            module.onEnable()
            plugin.logger.info("Loading plugin module successful: ${module.name}")
        }
    }

    fun reload() {
        pluginModules.forEach { module ->
            plugin.logger.info("Reloading plugin module: ${module.name}")
            module.onReload()
            plugin.logger.info("Reload plugin module successful: ${module.name}")
        }
    }
    fun shutdown() {
//        pluginModules.asReversed().forEach { module ->
//            module.onDisable()
//            plugin.logger.info("Disable plugin module: ${module.name}")
//        }
        stopKoin()
        HandlerList.unregisterAll(plugin)
        assertShutdown()
        plugin.logger.info("Shutting down plugin koin.")
    }
    private fun assertShutdown() {

    }
}