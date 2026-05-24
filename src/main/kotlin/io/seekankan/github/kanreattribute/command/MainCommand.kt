package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.command.admin.SubAdminCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.java.JavaPlugin


class MainCommand(
    val plugin: KanReAttribute
): CommandExecutor, TabCompleter, SubCommand<CommandSender>(
    "kanreattribute",
    subCommands = commandMapOf(
        SubReloadCommand(plugin),
        SubAdminCommand(plugin),
        SubStateCommand(plugin)
    ),
    usage = "/kra [reload|admin|state] [...]"

) {

//    private val messageManager: MessageService by inject()
//
//    val subCommands = arrayOf(
//        SubReloadCommand(plugin),
//        SubAdminCommand(plugin),
//        SubStateCommand(plugin)
//    )
//    val subCommandNames: Array<String>
//
//    init {
//        val temp = arrayOfNulls<String>(subCommands.size)
//        subCommands.forEachIndexed { index, s ->
//            temp[index] = s.command
//        }
//        subCommandNames = temp.requireNoNulls()
//    }
//
//    override fun onCommand(
//        sender: CommandSender,
//        command: Command,
//        label: String,
//        args: Array<String>
//    ): Boolean {
//        if (args.isEmpty()) {
//
//            return true
//        } else {
//            subCommands.forEach { subCommand ->
//                if (args[0].equals(subCommand.command, ignoreCase = true)) {
//                    return subCommand.onCommandBody(sender, command, label, args.copyOfRange(1, args.size))
//                }
//            }
//            messageManager.sendTo(sender,Message.COMMAND__NO_COMMAND)
//            return true
//        }
//    }
//
//    override fun onTabComplete(
//        sender: CommandSender,
//        command: Command,
//        alias: String,
//        args: Array<String>
//    ): List<String>? {
//        if (args.size == 1) {
//            val list: MutableList<String> = arrayListOf(*subCommandNames)
//            val latest = args[0]
//            CommandUtil.filter(list, latest)
//            return list
//        }
//        val subCommandName = args[0]
//        val subCommand: SubCommand = subCommands.firstOrNull {
//            it.command.equals(subCommandName, ignoreCase = true)
//        } ?: return null
//        val subArgs = args.copyOfRange(1, args.size)
//        return subCommand.onTabComplete(sender, command, alias, subArgs)
//    }


    fun setupCommand() {
        val command = (plugin as JavaPlugin).getCommand("kanreattribute")!!
        command.setExecutor(this)
        command.tabCompleter = this
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): Boolean {
        return onCommandBody(sender, ArgumentList(args))
    }

    override fun handleCommand(sender: CommandSender, args: ArgumentList): Boolean {
        messageService.sendParsedMessages(sender) {
            this.command.mainCommand.introduction
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<String>
    ): List<String>? {
        return onTabComplete(sender, args)
    }
}