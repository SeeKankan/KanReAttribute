package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.ConfigHolder
import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.message.ItemLoreParser
import io.seekankan.github.kanreattribute.message.MessageManager
import io.seekankan.github.kanreattribute.message.MessageService
import net.kyori.adventure.text.minimessage.MiniMessage
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class BaseConfigModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "BaseConfigModule"

//    private val audiences: BukkitAudiences by inject()
    private val messageManager: MessageManager by inject()
    private val messageService: MessageService by inject()
    private val itemLoreParser: ItemLoreParser by inject()

    override val koinModule: Module = module {
//        singleOf(BukkitAudiences::create).onClose {
//            it?.close()
//        }
//        single<BukkitAudiences> {
//            BukkitAudiences.create(get<KanReAttribute>())
//        }
        single {
            MiniMessage.miniMessage()
        }
        singleOf(::MessageManager)
        singleOf(::MessageService)
        singleOf(::ItemLoreParser)
        singleOf(::ConfigHolder)
    }
//    {
//        return module {
//            single<Message>(createdAtStart = true) {
//                Message.loadMessage(plugin)
//                Message.MARK_DO_NOT_USED
//            }
//            single<Config>(createdAtStart = true) {
//                plugin.saveDefaultConfig()
//                Config.snapshotDefaults()
//                Config.loadConfig(plugin)
//                Config
//            }
//        }
//    }

    override fun onEnable() {
        val configHolder = getKoin().get<ConfigHolder>()

        messageManager.loadMessage()
        itemLoreParser.loadConfig()

//        Message.loadMessage(plugin)

//        plugin.saveDefaultConfig()
        configHolder.loadConfig()
    }

    override fun onReload() {
        val configHolder = getKoin().get<ConfigHolder>()

        messageManager.loadMessage()
        itemLoreParser.loadConfig()
        configHolder.loadConfig()
    }

}