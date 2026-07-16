package io.seekankan.github.kanreattribute.command.legacy.admin

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.command.legacy.SubCommand
import io.seekankan.github.kanreattribute.command.legacy.commandMapOf
import org.bukkit.command.CommandSender

class SubAdminCommand(
    val plugin: KanReAttribute
): SubCommand<CommandSender>(
    command = "admin",
    subCommands = commandMapOf(
        SubAdminQueryCommand(plugin),
        SubAdminGiveItemCommand(plugin)
    ),
//    requirePermissions = listOf(
//        PermissionNode.Admin
//    ),
    usage = "/kra admin <query|giveitem> [...]"
) {
//    override fun handleCommand(
//        sender: CommandSender,
//        command: Command,
//        label: String
//    ): Boolean {
//
//        return true
//    }

}
