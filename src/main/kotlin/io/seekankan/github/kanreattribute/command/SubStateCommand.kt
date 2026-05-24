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
): SubCommand<Player>(
    command = "state",
    types = Player::class.java,
    usage = "/kra state"
) {
    private val stateGUIService: StateGUIService by inject()

    override fun handleCommand(sender: Player, args: ArgumentList): Boolean {
        stateGUIService.openGUI(sender)
        return true
    }

}