package io.seekankan.github.kanreattribute.message

import java.util.Locale.getDefault

enum class Message {

    COMMAND__NO_COMMAND,
    COMMAND__MUST_BE_PLAYER,
    COMMAND__INVALID_ARGUMENTS,
    COMMAND__CORRECT_USAGE,

    COMMAND__PLUGIN_RELOAD_START,
    COMMAND__PLUGIN_RELOAD_START_BY_SB,
    COMMAND__PLUGIN_RELOAD_SUCCESS,
    COMMAND__PLUGIN_RELOAD_FAIL,

    COMMAND__QUERY_REGISTERED,

    COMMAND__INVALID_ITEM_AMOUNT,
    COMMAND__ITEM_TYPE_NOT_FOUND,
    COMMAND__ITEM_INSTANCE_NOT_FOUND
    ;

//    companion object {
//        private val miniMessage: MiniMessage by lazy {
//            MiniMessage.miniMessage()
//        }
//        private lateinit var messageYML: YamlConfiguration
//
//        fun loadMessage(plugin: KanReAttribute) {
//            val file = File(plugin.dataFolder, "message.yml")
//            if (!file.exists()) {
//                plugin.logger.info("Create message.yml");
//                plugin.saveResource("message.yml", true);
//            }
//            messageYML = YamlConfiguration.loadConfiguration(file);
//        }
//    }

    val loc = name.lowercase(getDefault()).replace("__", ".")

//    fun getMessage(vararg args: Any): String {
//        val rawStr = messageYML.getString(loc, loc)!!
//        return MessageFormat.format(rawStr, *args).replace('&', '§')
//    }
//    fun getComponent(vararg args: Pair<String, Any>): Component {
//        val rawStr = messageYML.getString(loc, loc)!!
////        val a = Placeholder.parsed(args[0].component1(), args[0].component2().toString())
//        val placeholders = args.map { (papiName, value) ->
//            if(value is Component) {
//                Placeholder.component(papiName, value)
//            } else Placeholder.parsed(papiName, value.toString())
//        }.toTypedArray()
//        val component = miniMessage.deserialize(rawStr, *placeholders)
//        return component
//    }


}
//fun CommandSender.sendComponent(component: Component) {
//    if(PaperLib.isPaper()) {
//
//    } else {
//        val baseComponent = BukkitComponentSerializer.legacy().serialize(component)
//        this.sendMessage(baseComponent)
//    }
//
//}
//
//fun CommandSender.sendMessage(message: Message, vararg args: Any) {
//    this.sendMessage(message.getMessage(args))
//}
