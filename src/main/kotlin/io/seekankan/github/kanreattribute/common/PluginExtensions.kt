package io.seekankan.github.kanreattribute.common

import org.bukkit.Bukkit

const val PLUGIN_NAMESPACE = "kanreattribute"

fun isPluginExist(pluginName: String): Boolean {
    return Bukkit.getPluginManager().getPlugin(pluginName) != null
}
fun isVaultExist(): Boolean {
    return isPluginExist("Vault")
}