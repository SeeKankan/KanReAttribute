package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.gui.StateGUIService
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.koin.core.component.inject

class SubStateCommand(
    val plugin: KanReAttribute
): SubCommand(
    command = "state",
    subCommands = listOf()

) {
    private val stateGUIService: StateGUIService by inject()

    override fun onSubCommand(
        sender: CommandSender,
        command: Command,
        label: String
    ): Boolean {
        if(sender is Player) {
            stateGUIService.openGUI(sender)
        }
        return true
    }

}