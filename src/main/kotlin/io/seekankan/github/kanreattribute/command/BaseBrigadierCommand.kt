package io.seekankan.github.kanreattribute.command

import com.mojang.brigadier.builder.ArgumentBuilder
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.seekankan.github.kanreattribute.command.extensions.applyChild
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.permission.PermissionNode
import io.seekankan.github.kanreattribute.permission.PermissionService
import org.bukkit.command.CommandSender
import org.koin.core.component.inject
import kotlin.getValue

abstract class BaseBrigadierCommand<T : ArgumentBuilder<CommandSourceStack, T>>: BrigadierCommand<T> {
    protected val permissionService: PermissionService by inject()
    protected val messageManager: MessageManager by inject()
    protected val messageService: MessageService by inject()


    abstract val command: String

    open val subCommands: List<BrigadierCommand<*>> = listOf()
    open val requirePermissions: List<PermissionNode> = listOf()

    fun hasPermissions(sender: CommandSender): Boolean {
        val result = permissionService.hasAll(sender, requirePermissions)
        return result.isSuccess()
    }

    open fun checkRequires(ctx: CommandSourceStack): Boolean {
        val sender = ctx.sender
        return hasPermissions(sender) && isValidSender(ctx) && isValidExecutor(ctx) && otherRequires(ctx)
    }

    protected open fun isValidSender(sourceStack: CommandSourceStack): Boolean {
        return true
    }

    protected open fun isValidExecutor(sourceStack: CommandSourceStack): Boolean {
        return true
    }

    protected open fun otherRequires(sourceStack: CommandSourceStack): Boolean {
        return true
    }

    open fun applyChildren(builder: T) {
        subCommands.forEach { subCommand ->
            builder.applyChild(subCommand.init())
        }
    }

}