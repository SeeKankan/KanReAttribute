package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.Config
import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.coroutines.BukkitDispatcher
import io.seekankan.github.kanreattribute.coroutines.CoroutinesManager
import io.seekankan.github.kanreattribute.message.ItemLoreParser
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.message.MessageService
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.platform.bukkit.BukkitAudiences.create
import net.kyori.adventure.text.minimessage.MiniMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.onClose
import kotlin.getValue

class CoroutinesModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "CoroutinesModule"

    override val koinModule: Module = module {
        singleOf(::BukkitDispatcher)
        singleOf(::CoroutinesManager).onClose {
            it?.shutdown()
        }


    }

    override fun onEnable() {

    }

    override fun onReload() {

    }

}