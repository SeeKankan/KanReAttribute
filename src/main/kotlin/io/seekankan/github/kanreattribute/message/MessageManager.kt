package io.seekankan.github.kanreattribute.message

import io.seekankan.github.kanreattribute.PluginInfo

class MessageManager(
    private val pluginInfo: PluginInfo
) {
    lateinit var config: MessageConfig

    fun loadMessage() {
//        val file = File(pluginInfo.dataFolder, "message.yml")
//        if (!file.exists()) {
//            logger.info("Create message.yml");
//            pluginInfo.saveResource("message.yml", true);
//        }
        config = pluginInfo.loadYAML("message.yml")
    }
}