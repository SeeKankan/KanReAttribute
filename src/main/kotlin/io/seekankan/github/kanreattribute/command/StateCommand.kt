package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.seekankan.github.kanreattribute.ExecutableBrigadierCommand
import io.seekankan.github.kanreattribute.gui.StateGUIService
import io.seekankan.github.kanreattribute.permission.PermissionNode
import org.bukkit.entity.Player
import org.koin.core.component.inject
import kotlin.getValue

class StateCommand: ExecutableBrigadierCommand<LiteralArgumentBuilder<CommandSourceStack>>() {

    private val stateGUIService: StateGUIService by inject()

    override val requirePermissions: List<PermissionNode> = listOf(
        PermissionNode.User
    )

    override fun isValidExecutor(sourceStack: CommandSourceStack): Boolean {
        return sourceStack.executor is Player
    }

    override fun buildNode(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal(command)
    }

    override fun handleCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val executor = ctx.source.executor as? Player ?: return 0

        stateGUIService.openGUI(executor)
        return Command.SINGLE_SUCCESS
    }

    override val command: String = "state"
}