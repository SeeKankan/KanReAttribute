package io.seekankan.github.kanreattribute.di

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.command.BrigadierCommand
import io.seekankan.github.kanreattribute.command.BrigadierRootCommand
import io.seekankan.github.kanreattribute.command.MainCommand
import io.seekankan.github.kanreattribute.command.legacy.MainCommand as LegacyMainCommand
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


class CommandModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "CommandModule"

    override val koinModule: Module = module {
        single { LegacyMainCommand(plugin) }
        singleOf(::MainCommand) bind BrigadierRootCommand::class
    }

    override fun onEnable() {
        val koin = getKoin()

        val legacyMainCommand = koin.get<LegacyMainCommand>()
        legacyMainCommand.setupCommand()

        val rootCommands = koin.getAll<BrigadierRootCommand>()

        plugin.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            val registrar = commands.registrar()
            rootCommands.forEach {
                val commandNode = it.build()
                registrar.register(commandNode)
            }
        }
    }
    override fun onReload() {

    }


}