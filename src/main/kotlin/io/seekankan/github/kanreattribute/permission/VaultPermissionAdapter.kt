package io.seekankan.github.kanreattribute.permission

import io.seekankan.github.kanreattribute.common.isVaultExist
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class VaultPermissionAdapter: PermissionAdapter {
    companion object {
        fun canSetup(): Boolean {
            return isVaultExist() && Bukkit.getServicesManager().getRegistration(Permission::class.java) != null
        }
    }
    private lateinit var vaultPermission: Permission

    override fun hasPermission(
        commandSender: CommandSender,
        permission: String
    ): Boolean {
        return if (commandSender !is Player) vaultPermission.has(commandSender, permission)
        else vaultPermission.has(commandSender, permission)
    }



    override fun setup() {
        if(!isVaultExist()) {
            throw IllegalStateException("Vault doesn't exist.")
        }
        val rsp = Bukkit.getServicesManager().getRegistration(Permission::class.java)
            ?: throw IllegalStateException("Vault rsp doesn't exist.")

        vaultPermission = rsp.provider
    }
}