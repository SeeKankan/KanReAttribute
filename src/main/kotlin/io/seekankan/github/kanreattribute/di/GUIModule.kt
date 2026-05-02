package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.gui.GUIConfig
import io.seekankan.github.kanreattribute.gui.GUIProtectService
import io.seekankan.github.kanreattribute.gui.StateGUIService
import org.bukkit.Bukkit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.onClose
import kotlin.getValue

class GUIModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "GUIModule"

    //    private val audiences: BukkitAudiences by inject()
    private val messageManager: MessageManager by inject()
    private val guiProtectService: GUIProtectService by inject()
    private val guiConfig: GUIConfig by inject()
//    private val config: Config by inject()

    override val koinModule: Module = module {
        singleOf(::GUIConfig)

        singleOf(::GUIProtectService).onClose {
            it?.clearAllInventories()
        }
        singleOf(::StateGUIService)
//        singleOf(::ShowPlayerAttributeGUI)
    }

    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(guiProtectService, plugin)
        guiConfig.loadConfig()

    }

    override fun onReload() {
        guiConfig.loadConfig()
    }

}