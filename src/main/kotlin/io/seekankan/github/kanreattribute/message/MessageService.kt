package io.seekankan.github.kanreattribute.message

import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender

class MessageService(
    private val audiences: BukkitAudiences,
    private val miniMessage: MiniMessage,
    private val messageManager: MessageManager
) {

//    private lateinit var messageYML: YamlConfiguration



//    fun getMessage(message: Message, vararg args: Any): String {
//        val rawStr = messageYML.getString(message.loc, message.loc)!!
//        return MessageFormat.format(rawStr, *args).replace('&', '§')
//    }
//    @Deprecated("")
//    fun getComponent(message: Message, vararg args: Pair<String, Any>): Component {
//        val rawStr = messageYML.getString(message.loc, message.loc)!!
//        val placeholders = args.toPlaceholderArray()
//        val component = miniMessage.deserialize(rawStr, *placeholders)
//        return component
//    }
    fun getComponent(message: String, vararg args: Pair<String, *>): Component {
        val placeholders = args.toPlaceholderArray()
        return miniMessage.deserialize(message, *placeholders)
    }
    fun sendComponent(sender: CommandSender, component: Component) {
        audiences.sender(sender).sendMessage(component)
    }
//    @Deprecated("")
//    fun sendTo(sender: CommandSender, message: Message, vararg args: Pair<String, Any>) {
//        val component = getComponent(message, *args)
//        sendComponent(sender, component)
//    }
    fun sendParsed(sender: CommandSender, messageFunction: MessageConfig.() -> String) {
        val message = messageManager.config.messageFunction()
        val component = getComponent(message)
        sendComponent(sender, component)
    }
    fun sendParsedMessages(sender: CommandSender, messageFunction: MessageConfig.() -> List<String>) {
        val messages = messageManager.config.messageFunction()
        messages.forEach {
            val component = getComponent(it)
            sendComponent(sender, component)
        }
    }
    fun sendParsed(sender: CommandSender, vararg args: Pair<String, *>, messageFunction: MessageConfig.() -> String) {
        val message = messageManager.config.messageFunction()
        val component = getComponent(message, *args)
        sendComponent(sender, component)
    }


    fun toLegacyText(text: String, vararg args: Pair<String, *>): String {
        val component = miniMessage.deserialize(text, *args.toPlaceholderArray())
        return LegacyComponentSerializer.legacySection().serialize(component)
    }
    fun toGsonStringList(textList: List<String>, vararg args: Pair<String, Any>): List<String> {
//        val mutableList = ArrayList<String?>(textList.size)
//        return textList.flatMap {
//            val component = miniMessage.deserialize(it, *args.toPlaceholderArray())
//            val fullString = LegacyComponentSerializer.legacySection().serialize(component)
//            fullString.split("<newline>|\\n")
//        }.map {
//            it.trim()
//        }.filter {
//            it.isNotEmpty()
//        }
        return toGsonStringList(textList, *args.toPlaceholderArray())
    }
    fun toGsonStringList(textList: List<String>, vararg tagResolvers: TagResolver): List<String> {
        return textList.flatMap {
            val component = miniMessage.deserialize(it, *tagResolvers)
            val splitComponents = component.splitByNewLine()
            splitComponents
//            val fullString = LegacyComponentSerializer.legacySection().serialize(component)
//            GsonComponentSerializer.gson().serialize(component)
//            plugin.logger.info("$json")
//            fullString.split("\n")
        }.map {
            GsonComponentSerializer.gson().serialize(it)
        }
//        val separator = Component.newline()
//        val components = textList.map {
//            miniMessage.deserialize(it, *tagResolvers).spliterator()
//        }
//
//        return "a".split()
    }
}
