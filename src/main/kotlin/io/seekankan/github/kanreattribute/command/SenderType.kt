package io.seekankan.github.kanreattribute.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

enum class SenderType {
    PLAYER,
    CONSOLE;
}
fun CommandSender.getSenderType(): SenderType {
    return if (this is Player) {
        SenderType.PLAYER
    } else SenderType.CONSOLE
}