package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.Commands
import io.seekankan.github.kanreattribute.permission.PermissionNode
import io.papermc.paper.command.brigadier.CommandSourceStack as PaperCommandSourceStack

class MainCommand: BrigadierRootCommand() {
    override val command: String = "kra"
    override val subCommands: List<BrigadierCommand<*>> = listOf(
        ReloadCommand()
    )
    override val requirePermissions: List<PermissionNode> = listOf(
        PermissionNode.User
    )

    override fun buildNode(): LiteralArgumentBuilder<PaperCommandSourceStack> {
        return Commands.literal(command)
    }

    override fun isValidExecutor(sourceStack: PaperCommandSourceStack): Boolean {
        return sourceStack.executor != null
    }

    override fun handleCommand(ctx: CommandContext<PaperCommandSourceStack>): Int {
        val executor = ctx.source.executor ?: return 0

        messageService.sendParsedMessages(executor) {
            this.command.mainCommand.introduction
        }
        return Command.SINGLE_SUCCESS
    }
}