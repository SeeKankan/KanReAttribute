package io.seekankan.github.kanreattribute.registry.impl

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.common.ItemConditionTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.common.registryKeyOf
import io.seekankan.github.kanreattribute.item.condition.ItemCondition
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry

class ItemConditionRegistry(
    private val pluginInfo: PluginInfo
): CopyOnWriteRegistry<ItemCondition, ItemConditionTag>() {
    override val registerableTypeName: String = "ItemCondition"
    override val uniqueName: NamespacedKeyOf<RegistryTag> = registryKeyOf(pluginInfo, "item_condition")

}