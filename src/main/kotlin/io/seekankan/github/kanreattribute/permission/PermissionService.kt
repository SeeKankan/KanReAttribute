package io.seekankan.github.kanreattribute.permission

import org.bukkit.command.CommandSender

class PermissionService {
    private lateinit var adapter: PermissionAdapter

    fun loadPermission() {
        adapter = if(VaultPermissionAdapter.canSetup()) {
            VaultPermissionAdapter()
        } else if(VanillaPermissionAdapter.canSetup()) {
            VanillaPermissionAdapter()
        } else {
            throw IllegalStateException("Cannot init permission adapter")
        }
        adapter.setup()
    }
    fun has(commandSender: CommandSender, permissionNode: PermissionNode): Boolean {
        return adapter.hasPermission(commandSender, permissionNode.node)
    }
    fun hasAll(commandSender: CommandSender, permissionNodes: List<PermissionNode>): PermissionResult {
        val missing = mutableListOf<PermissionNode>()

        permissionNodes.forEach { permNode ->
            if(!has(commandSender, permNode)) missing.add(permNode)
        }
        return if(missing.isEmpty()) {
            PermissionResult.Success
        } else {
            PermissionResult.Failed(
                permissionNodes,
                missing
            )
        }
    }
}