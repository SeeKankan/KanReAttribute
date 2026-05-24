package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.extensions.isInstanceOf
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.message.MessageService
import io.seekankan.github.kanreattribute.permission.PermissionNode
import io.seekankan.github.kanreattribute.permission.PermissionResult
import io.seekankan.github.kanreattribute.permission.PermissionService
import org.bukkit.command.CommandSender
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class SubCommand<in T: CommandSender>(
    val command: String,
    val subCommands: CommandMap = emptyCommandMap(),
    val types: Class<*> = CommandSender::class.java,
    val requirePermissions: List<PermissionNode> = listOf(),
    val usage: String
): KoinComponent {
    //        if(types.isEmpty()) throw IllegalArgumentException("Command type cannot be empty")
    val lowerCaseCommand: String = command.lowercase()

    protected val permissionService: PermissionService by inject()
    protected val messageManager: MessageManager by inject()
    protected val messageService: MessageService by inject()

//    val subCommandNames: List<String> by lazy {
//        subCommands.map {
//            it.command
//        }
//    }

    protected inline fun <R> checkPermission(sender: CommandSender, whenFailed: (PermissionResult.Failed) -> R) {
        when(val result = hasPermissions(sender)) {
            is PermissionResult.Failed -> {
                whenFailed(result)
            }

            PermissionResult.Success -> {}
        }
    }

    protected fun isValidSenderType(sender: CommandSender): Boolean {
        return sender isInstanceOf types
    }
    protected fun sendInvalid(sender: CommandSender) {
        messageService.sendParsed(sender) {
            command.common.noCommand
        }
    }
//    protected fun sendMustBePlayer(sender: CommandSender) {
//        messageService.sendParsed(sender) {
//            command.common.mustBePlayer
//        }
//    }
//    protected fun sendMustBeConsole(sender: CommandSender) {
//        messageService.sendParsed(sender) {
//            command.common.mustBeConsole
//        }
//    }

    protected fun sendInvalidSenderType(sender: CommandSender) {
        val formatRequireSenderType = messageService.formatSenderType(types)
        messageService.sendParsed(sender, "sender_type" to formatRequireSenderType) {
            command.common.invalidSenderType
        }
    }
    fun hasPermissions(sender: CommandSender): PermissionResult {
        val result = permissionService.hasAll(sender, requirePermissions)
        return result
    }
    protected fun sendMissingPermission(sender: CommandSender, result: PermissionResult.Failed) {
        messageService.sendParsed(sender) {
            command.common.noPermission
        }
        val missingPermissionString = result.requirePermissions.joinToString()
        messageService.sendParsed(sender, "missing_permission" to missingPermissionString) {
            command.common.missingPermission
        }
    }

    protected fun sendCorrectUsage(sender: CommandSender) {
        messageService.sendParsed(sender) {
            command.common.invalidArguments
        }
        messageService.sendParsed(sender, "usage" to usage) {
            command.common.correctUsage
        }
    }


    open fun onCommandBody(
        sender: CommandSender,
        args: ArgumentList
    ): Boolean {
//        if(!args.hasNext()) {
//            when(val result = hasPermissions(sender)) {
//                is PermissionResult.Failed -> {
//                    sendMissingPermission(sender, result)
//                    return true
//                }
//                PermissionResult.Success -> {
//                    if(!isValidSenderType(sender)) {
//                        sendInvalidSenderType(sender)
//                        return true
//                    }
//                    sender as T
//                    return handleCommand(sender, args)
//                }
//            }
//        } else {
//            return handleWithArgument(sender, args)
//        }
        val hasArgument = args.hasNext()
        if(hasArgument && subCommands.isNotEmpty()) {
            return handleSubCommand(sender, args)
        }
        checkPermission(sender) {
            sendMissingPermission(sender, it)
            return true
        }
        if(!isValidSenderType(sender)) {
            sendInvalidSenderType(sender)
            return true
        }
        sender as T
        return if(!hasArgument) {
            handleCommand(sender, args)
        } else {
            handleWithArgument(sender, args)
        }
    }
    open fun handleCommand(sender: T, args: ArgumentList): Boolean{
        sendCorrectUsage(sender)
//        sendInvalid(sender)
        return true
    }
    open fun handleSubCommand(sender: CommandSender, args: ArgumentList): Boolean {
        val popArg = args.pop() ?: return true
        val exeSubCommand = subCommands[popArg]
        if(exeSubCommand == null) {
            sendCorrectUsage(sender)
            return true
        }
        return exeSubCommand.onCommandBody(sender, args)
    }
    open fun handleWithArgument(sender: T, args: ArgumentList): Boolean {
        sendCorrectUsage(sender)
        return true
    }

    open fun onTabComplete(
        sender: CommandSender,
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
//            val list: MutableList<String> = subCommandNames.toMutableList()
//            val latest = args[0]
//            list.retainByPrefix(latest)
//            list.removeIf {
//                val subCommand =
//            }
//            return list
            return subCommands.matchCommandsByPrefix(sender, args[0])
        }
        val subCommandName = args[0]
//        val subCommand: SubCommand = subCommands.firstOrNull {
//            it.command.equals(subCommandName, ignoreCase = true)
//        } ?: return null
//        val subArgs = args.copyOfRange(1, args.size)
//        return subCommand.onTabComplete(sender, command, alias, subArgs)
        val subArgs = args.copyOfRange(1, args.size)
        return subCommands[subCommandName]?.onTabComplete(sender, subArgs)
    }
}