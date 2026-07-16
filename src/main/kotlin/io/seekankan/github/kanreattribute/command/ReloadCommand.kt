package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.seekankan.github.kanreattribute.PluginReloader
import io.seekankan.github.kanreattribute.permission.PermissionNode
import org.bukkit.Bukkit
import org.koin.core.component.inject
import java.util.logging.Logger

class ReloadCommand: BrigadierCommand<LiteralArgumentBuilder<CommandSourceStack>>() {
    private val logger: Logger by inject()
    private val reloader: PluginReloader by inject()

    override val command: String = "reload"
    override val requirePermissions: List<PermissionNode> = listOf(
        PermissionNode.Admin.Manage
    )

    override fun buildNode(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal(command)
    }

    override fun handleCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val sender = ctx.source.sender
//        val executor = ctx.source.executor
//
//        val consoleSender = Bukkit.getConsoleSender()

        messageService.sendParsed(sender) {
            command.plugin.reloadStart
        }

        messageService.sendParsed(Bukkit.getServer(), "sender" to sender.name) {
            command.plugin.reloadStartBySb
        }

        try {
            reloader.reload()

            messageService.sendParsed(sender) {
                command.plugin.reloadSuccess
            }
        } catch (e: Exception) {
            logger.severe("KanReAttribute reload failed(start by ${sender.name}): ${e.message}")
            e.printStackTrace()

            messageService.sendParsed(sender) {
                this.command.plugin.reloadFail
            }
        }

        return Command.SINGLE_SUCCESS
    }
}