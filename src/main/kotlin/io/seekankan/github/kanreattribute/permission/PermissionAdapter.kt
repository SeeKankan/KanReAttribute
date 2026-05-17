package io.seekankan.github.kanreattribute.permission

import org.bukkit.command.CommandSender

interface PermissionAdapter {
    fun hasPermission(commandSender: CommandSender, permission: String): Boolean

//    fun canSetup(): Boolean
    fun setup()
}