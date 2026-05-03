package io.seekankan.github.kanreattribute.message

import com.fasterxml.jackson.core.type.TypeReference
import io.seekankan.github.kanreattribute.common.ResourceLocation
import io.seekankan.github.kanreattribute.util.saveFileAndReadYAML
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.JoinConfiguration
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.plugin.Plugin
import java.io.File

class ItemLoreParser(
    private val plugin: Plugin,
    private val messageManager: MessageManager
) {
    private val loreConfigDir = ResourceLocation.TAG_RESOLVER_FOLDER
    private val itemStyleConfigFile = File(loreConfigDir, "item_style.yml").path

    lateinit var itemStyleMap: Map<String, String>
    lateinit var rootResolver: TagResolver

    fun loadConfig() {
        itemStyleMap = saveFileAndReadYAML(plugin, object: TypeReference<Map<String, String>>(){}, itemStyleConfigFile)

        val miniMessage = messageManager.miniMessage
        val builder = TagResolver.builder()
        itemStyleMap.forEach { (key, templateString) ->
            builder.resolver(TagResolver.resolver(key) { args, context ->
                Tag.inserting {
                    context.deserialize(templateString)
                }
            })
        }
        rootResolver = builder.build()
    }
    fun parseList(eachLoopTemplate: String, dataList: List<Map<String, String>>, separator: Component = Component.newline()): Component {
        val parsedLines =  dataList.map { rowData ->
            val rowResolver = TagResolver.builder()
                .resolver(rootResolver)
                .apply {
                    rowData.forEach { (key, value) ->
                        resolver(TagResolver.resolver(key) { args, context ->
                            Tag.inserting(context.deserialize(value))
                        })
                    }
                }.build()
            messageManager.miniMessage.deserialize(eachLoopTemplate, rowResolver)
        }
        return Component.join(JoinConfiguration.separator(separator), parsedLines)
    }

    fun parseGsonLore(textList: List<String>, vararg args: Pair<String, Any>): List<String> {
        val itemResolver = TagResolver.builder()
            .resolver(rootResolver)
            .apply {
                args.forEach { (key, value) ->
                    resolver(TagResolver.resolver(key) { _, context ->
                        val insertComponent = value as? Component ?: context.deserialize(value.toString())
                        Tag.inserting(insertComponent)
                    })
                }
            }.build()

        return messageManager.toGsonStringList(textList, itemResolver)
    }

}