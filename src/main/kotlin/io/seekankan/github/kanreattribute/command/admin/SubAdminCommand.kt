package io.seekankan.github.kanreattribute.command.admin

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.command.SubCommand
import io.seekankan.github.kanreattribute.command.commandMapOf

class SubAdminCommand(
    val plugin: KanReAttribute
): SubCommand(
    command = "admin",
    subCommands = commandMapOf(
        SubAdminQueryCommand(plugin),
        SubAdminGiveItemCommand(plugin)
    )

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
