package io.seekankan.github.kanreattribute.registry.impl

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.attribute.subattribute.SubAttribute
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.common.SubAttributeTag
import io.seekankan.github.kanreattribute.common.registryKeyOf
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry

class SubAttributeRegistry(
    private val pluginInfo: PluginInfo
): CopyOnWriteRegistry<SubAttribute, SubAttributeTag>() {
    override val registerableTypeName: String = "SubAttribute"
    override val uniqueName: NamespacedKeyOf<RegistryTag> = registryKeyOf(pluginInfo, "sub_attribute")
}