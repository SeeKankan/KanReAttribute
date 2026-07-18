package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.seekankan.github.kanreattribute.extensions.findLogger
import io.seekankan.github.kanreattribute.permission.PermissionNode


class AdminCommand: BaseBrigadierCommand<LiteralArgumentBuilder<CommandSourceStack>>() {
    private val logger = findLogger()

    override val command: String = "admin"
    override val subCommands: List<BrigadierCommand<*>> = listOf(
        GiveItemCommand()
    )
    override val requirePermissions: List<PermissionNode> = listOf(

    )

    fun buildNode(): LiteralArgumentBuilder<CommandSourceStack> {
        return Commands.literal(command)
    }

    override fun init(): LiteralArgumentBuilder<CommandSourceStack> {
        return buildNode().requires { ctx ->
            checkRequires(ctx)
        }.apply {
            applyChildren(this)
        }
    }

}