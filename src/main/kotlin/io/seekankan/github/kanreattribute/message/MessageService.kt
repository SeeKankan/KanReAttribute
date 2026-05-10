package io.seekankan.github.kanreattribute.message

import io.seekankan.github.kanreattribute.KanReAttribute
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.text.MessageFormat

class MessageService(
    private val audiences: BukkitAudiences,
    private val miniMessage: MiniMessage,
) {
//    val miniMessage: MiniMessage by lazy {
//        MiniMessage.miniMessage()
//    }
    private lateinit var messageYML: YamlConfiguration



//    fun getMessage(message: Message, vararg args: Any): String {
//        val rawStr = messageYML.getString(message.loc, message.loc)!!
//        return MessageFormat.format(rawStr, *args).replace('&', '§')
//    }
    fun getComponent(message: Message, vararg args: Pair<String, Any>): Component {
        val rawStr = messageYML.getString(message.loc, message.loc)!!
//        val a = Placeholder.parsed(args[0].component1(), args[0].component2().toString())
//        val placeholders = args.map { (papiName, value) ->
//            if(value is Component) {
//                Placeholder.component(papiName, value)
//            } else Placeholder.parsed(papiName, value.toString())
//        }.toTypedArray()
        val placeholders = args.toPlaceholderArray()
        val component = miniMessage.deserialize(rawStr, *placeholders)
        return component
    }
    fun sendComponent(sender: CommandSender, component: Component) {
        audiences.sender(sender).sendMessage(component)
    }
    fun sendTo(sender: CommandSender, message: Message, vararg args: Pair<String, Any>) {
        val component = getComponent(message, *args)
        sendComponent(sender, component)
    }
    fun toLegacyText(text: String, vararg args: Pair<String, Any>): String {
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
