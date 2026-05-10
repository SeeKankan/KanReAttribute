package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.message.Message
import io.seekankan.github.kanreattribute.message.MessageService
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.permissions.Permission
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class SubCommand(
    val command: String,
    val subCommands: List<SubCommand> = emptyList(),
    val types: Array<SenderType> = arrayOf(SenderType.PLAYER, SenderType.CONSOLE),
    val requirePermissions: Array<Permission> = arrayOf()
): KoinComponent {
    protected val messageManager: MessageService by inject()

    val subCommandNames: List<String> by lazy {
        subCommands.map {
            it.command
        }
    }

    open fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        if(args.isEmpty()) {
            return onSubCommand(sender, command, label)
        } else {
            subCommands.forEach { subCommand ->
                if(args[0].equals(subCommand.command, ignoreCase = true)) {
                    return subCommand.onCommand(sender, command, label, args.copyOfRange(1, args.size))
                }
            }
            messageManager.sendTo(sender,Message.COMMAND__NO_COMMAND)
//            sender.sendMessage(Message.COMMAND__NO_COMMAND.getMessage())
            return true
        }
    }
    open fun onSubCommand(sender: CommandSender, command: Command, label: String): Boolean {
        messageManager.sendTo(sender,Message.COMMAND__NO_COMMAND)
//        sender.sendMessage(Message.COMMAND__NO_COMMAND.getMessage())
        return true
    }

    open fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
//        if(args.isEmpty()) {
//            return null
//        } else {
//        sender.sendMessage(javaClass.simpleName)
//        sender.sendMessage(subCommands.toString())
//        sender.sendMessage(subCommandNames.toString())
//        sender.sendMessage(args)
        if (args.size == 1) {
            val list: MutableList<String> = subCommandNames.toMutableList()
            val latest = args[0]
            CommandUtil.filter(list, latest)
            return list
        }
        val subCommandName = args[0]
        val subCommand: SubCommand = subCommands.firstOrNull {
            it.command.equals(subCommandName, ignoreCase = true)
        } ?: return null
        val subArgs = args.copyOfRange(1, args.size)
        return subCommand.onTabComplete(sender, command, alias, subArgs)
//        }
    }
}