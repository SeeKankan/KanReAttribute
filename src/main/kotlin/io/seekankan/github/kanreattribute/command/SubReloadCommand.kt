package io.seekankan.github.kanreattribute.command

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginReloader
import io.seekankan.github.kanreattribute.message.Message
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.koin.core.component.inject

class SubReloadCommand(
    val plugin: KanReAttribute,
): SubCommand(
    "reload"
) {
    private val reloader: PluginReloader by inject()

    override fun onSubCommand(
        sender: CommandSender,
        command: Command,
        label: String
    ): Boolean {
        try {
            val consoleSender = Bukkit.getConsoleSender()
            messageManager.sendTo(sender,Message.COMMAND__PLUGIN_RELOAD_START)
//            sender.sendMessage(Message.COMMAND__PLUGIN_RELOAD_START.getMessage())
            messageManager.sendTo(consoleSender,Message.COMMAND__PLUGIN_RELOAD_START_BY_SB, "sender" to sender.name)
//            Bukkit.getConsoleSender().sendMessage(Message.COMMAND__PLUGIN_RELOAD_START_BY_SB.getMessage())
//            plugin.pluginModuleManager.reload()
            reloader.reload()
            messageManager.sendTo(sender, Message.COMMAND__PLUGIN_RELOAD_SUCCESS)
//            sender.sendMessage(Message.COMMAND__PLUGIN_RELOAD_SUCCESS.getMessage())
//            consoleSender.sendMessage(Message.COMMAND__PLUGIN_RELOAD_SUCCESS.getMessage())
            messageManager.sendTo(sender, Message.COMMAND__PLUGIN_RELOAD_SUCCESS)
            return true
        } catch(e: Exception) {
            plugin.logger.severe("Plugin ${plugin.name} load failed: ${e.message}")
            messageManager.sendTo(sender, Message.COMMAND__PLUGIN_RELOAD_FAIL)
//            sender.sendMessage(Message.COMMAND__PLUGIN_RELOAD_FAIL.getMessage())
            e.printStackTrace()
            return false
        }
    }
}