package io.seekankan.github.kanreattribute.registry.impl

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.common.ItemCreateHandlerTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.common.registryKeyOf
import io.seekankan.github.kanreattribute.item.itemcreate.ItemCreateHandler
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry

class ItemCreateHandlerRegistry(
    private val pluginInfo: PluginInfo
): CopyOnWriteRegistry<ItemCreateHandler, ItemCreateHandlerTag>() {
    override val registerableTypeName: String = "ItemCreateHandle"
    override val uniqueName: NamespacedKeyOf<RegistryTag> = registryKeyOf(pluginInfo, "item_create_handle")

}