package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.command.MainCommand
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.dsl.module


class CommandModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "CommandModule"

    override val koinModule: Module = module {
        single { MainCommand(plugin) }
    }

    override fun onEnable() {
        val mainCommand = getKoin().get<MainCommand>()
        mainCommand.setupCommand()
    }
    override fun onReload() {

    }


}