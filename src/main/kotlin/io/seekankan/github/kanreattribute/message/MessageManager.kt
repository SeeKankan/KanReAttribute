package io.seekankan.github.kanreattribute.message

import io.seekankan.github.kanreattribute.PluginInfo

class MessageManager(
    private val pluginInfo: PluginInfo,
    private val messageConfigHolder: MessageConfigHolder
) {

//    lateinit var config: MessageConfig
    val config: MessageConfig
    get() = messageConfigHolder.currentConfig

    fun loadMessage() {
//        config = pluginInfo.loadYAML("message.yml")
    }
}