package io.seekankan.github.kanreattribute.message

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.util.CreateByProviderConfigHolder
import java.io.File

class MessageConfigHolder(
    override val pluginInfo: PluginInfo
): CreateByProviderConfigHolder<MessageConfig>() {
    override val configFile: File = pluginInfo.dataFolder
        .resolve("message.yml")
    override val configClass: Class<MessageConfig> = MessageConfig::class.java

    override fun createDefaultConfig(): MessageConfig {
        return MessageConfig()
    }
}