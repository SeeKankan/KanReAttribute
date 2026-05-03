package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.item.ItemService
import io.seekankan.github.kanreattribute.item.condition.ItemCondition
import io.seekankan.github.kanreattribute.item.condition.impl.ItemSlotCondition
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.item.finder.ItemFinder
import io.seekankan.github.kanreattribute.item.finder.impl.VanillaInventoryItemFinder
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateHandler
import io.seekankan.github.kanreattribute.item.itemcreate.ItemFactory
import io.seekankan.github.kanreattribute.item.itemcreate.ItemMetaAssembler
import io.seekankan.github.kanreattribute.item.itemcreate.impl.SetItemMetaHandler
import io.seekankan.github.kanreattribute.item.itemcreate.impl.WriteItemTypeHandler
import io.seekankan.github.kanreattribute.item.itemtype.ConfigurationItemType
import io.seekankan.github.kanreattribute.item.manager.ItemConditionManager
import io.seekankan.github.kanreattribute.item.manager.ItemFinderManager
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import io.seekankan.github.kanreattribute.item.message.ItemDefinitions
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class ItemModule(
    private val plugin: KanReAttribute,
): PluginModule, KoinComponent {
    companion object {
        init {
            ConfigurationSerialization.registerClass(ConfigurationItemType::class.java, "ConfigurationItemType")
        }
    }
    override val name: String = "ItemModule"

    private val itemTypeManager: ItemTypeManager by inject()
    private val itemFinderManager: ItemFinderManager by inject()
    private val itemConditionManager: ItemConditionManager by inject()
    private val itemDefinitions: ItemDefinitions by inject()
    private val itemFactory: ItemFactory by inject()

    override val koinModule: Module = module {
        singleOf(::ItemTypeManager)
        singleOf(::ItemFinderManager)
        singleOf(::ItemConditionManager)
        singleOf(::ItemDefinitions)
        singleOf(::ItemMetaAssembler)
        singleOf(::ItemFactory)

        singleOf(::ItemService)

        singleOf(::VanillaInventoryItemFinder) bind ItemFinder::class
        singleOf(::ItemSlotCondition) bind ItemCondition::class
        singleOf(::WriteItemTypeHandler) bind ItemCreateHandler::class
        singleOf(::SetItemMetaHandler) bind ItemCreateHandler::class
    }

    override fun onEnable() {
        val itemTypes = arrayOf<ItemType>(

        )
        val itemFinders = getKoin().getAll<ItemFinder>()
        val itemConditions = getKoin().getAll<ItemCondition>()
        val itemCreateHandlers = getKoin().getAll<ItemCreateHandler>()

        itemFinders.forEach {
            itemFinderManager.itemFinderRegistry.registerPersistent(it)
        }
        itemConditions.forEach {
            itemConditionManager.itemConditionRegistry.registerPersistent(it)
        }
        itemCreateHandlers.forEach {
            itemFactory.itemCreateHandlerRegistry.registerPersistent(it)
        }


        loadReloadable()
    }
    override fun onReload() {
        loadReloadable()
    }

    private fun loadReloadable() {
        itemTypeManager.itemTypeRegistry.clearTransient()
        itemTypeManager.loadAllYMLItemTypes()
        itemDefinitions.loadConfig()
    }
}