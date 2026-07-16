package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import io.papermc.paper.command.brigadier.CommandSourceStack as PaperCommandSourceStack
import io.seekankan.github.kanreattribute.command.extensions.applyChild
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.permission.PermissionNode
import io.seekankan.github.kanreattribute.permission.PermissionService
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BrigadierCommand<T: ArgumentBuilder<PaperCommandSourceStack, T>>: KoinComponent {
    protected val permissionService: PermissionService by inject()
    protected val messageManager: MessageManager by inject()
    protected val messageService: MessageService by inject()


    abstract val command: String

    open val subCommands: List<BrigadierCommand<*>> = listOf()
    open val requirePermissions: List<PermissionNode> = listOf()

    protected abstract fun buildNode(): T

    fun hasPermissions(sender: CommandSender): Boolean {
        val result = permissionService.hasAll(sender, requirePermissions)
        return result.isSuccess()
    }

    protected open fun isValidSender(sourceStack: PaperCommandSourceStack): Boolean {
        return true
    }
    protected open fun isValidExecutor(sourceStack: PaperCommandSourceStack): Boolean {
        return true
    }

    protected open fun otherRequires(sourceStack: PaperCommandSourceStack): Boolean { return true }

    protected abstract fun handleCommand(ctx: CommandContext<PaperCommandSourceStack>): Int


    open fun init(): T {
        return buildNode().requires { ctx ->
            val sender = ctx.sender
            hasPermissions(sender) && isValidSender(ctx) && isValidExecutor(ctx) && otherRequires(ctx)
        }.executes { ctx ->
            handleCommand(ctx)
        }.apply {
            subCommands.forEach { subCommand ->
                applyChild(subCommand.init())
            }
        }
    }

}