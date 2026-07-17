package io.seekankan.github.kanreattribute.registry.impl

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.common.ItemFinderTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.common.registryKeyOf
import io.seekankan.github.kanreattribute.item.finder.ItemFinder
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry


class ItemFinderRegistry(
    private val pluginInfo: PluginInfo
): CopyOnWriteRegistry<ItemFinder, ItemFinderTag>() {
    override val registerableTypeName: String = "ItemFinder"
    override val uniqueName: NamespacedKeyOf<RegistryTag> = registryKeyOf(pluginInfo, "item_finder")

}