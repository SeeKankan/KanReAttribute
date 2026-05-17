package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.gui.StateGUIService
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.koin.core.component.inject
import java.util.EnumSet

class SubStateCommand(
    val plugin: KanReAttribute
): SubCommand(
    command = "state",
    types = EnumSet.of(SenderType.PLAYER)
) {
    private val stateGUIService: StateGUIService by inject()

    override fun handleCommand(sender: CommandSender, args: ArgumentList): Boolean {
        if(sender is Player) {
            stateGUIService.openGUI(sender)
        }
        return true
    }

}