package io.seekankan.github.kanreattribute.permission

import org.bukkit.command.CommandSender

class VanillaPermissionAdapter: PermissionAdapter {
    companion object {
        fun canSetup(): Boolean = true
    }
    override fun hasPermission(commandSender: CommandSender, permission: String): Boolean {
        return commandSender.hasPermission(permission)
    }

    override fun setup() {

    }
}