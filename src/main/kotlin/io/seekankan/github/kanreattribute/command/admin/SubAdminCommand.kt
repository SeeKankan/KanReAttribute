package io.seekankan.github.kanreattribute.command.admin

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.command.SubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class SubAdminCommand(
    val plugin: KanReAttribute
): SubCommand(
    command = "admin",
    subCommands = listOf(
        SubAdminQueryCommand(plugin),
        SubAdminGiveItemCommand(plugin)
    )

) {
//    override fun onSubCommand(
//        sender: CommandSender,
//        command: Command,
//        label: String
//    ): Boolean {
//
//        return true
//    }

}
