package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.message.Message
import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.command.admin.SubAdminCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class MainCommand(
    val plugin: KanReAttribute
): CommandExecutor, TabCompleter, KoinComponent {

    private val messageManager: MessageService by inject()

    val subCommands = arrayOf(
        SubReloadCommand(plugin),
        SubAdminCommand(plugin),
        SubStateCommand(plugin)
    )
    val subCommandNames: Array<String>

    init {
        val temp = arrayOfNulls<String>(subCommands.size)
        subCommands.forEachIndexed { index, s ->
            temp[index] = s.command
        }
        subCommandNames = temp.requireNoNulls()
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("---KanReAttribute---") //TODO Print the use of the kanreattribute
            return true
        } else {
            subCommands.forEach { subCommand ->
                if (args[0].equals(subCommand.command, ignoreCase = true)) {
                    return subCommand.onCommand(sender, command, label, args.copyOfRange(1, args.size))
                }
            }
            messageManager.sendTo(sender,Message.COMMAND__NO_COMMAND)
            return true
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        if (args.size == 1) {
            val list: MutableList<String> = arrayListOf(*subCommandNames)
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
    }

    fun setupCommand() {
        val command = plugin.getCommand("kanreattribute")!!
        command.setExecutor(this)
        command.tabCompleter = this
    }
}