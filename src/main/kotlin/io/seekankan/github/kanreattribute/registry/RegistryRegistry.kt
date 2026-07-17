package io.seekankan.github.kanreattribute.registry

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.common.registryKeyOf

class RegistryRegistry(
    private val pluginInfo: PluginInfo
): CopyOnWriteRegistry<CopyOnWriteRegistry<*, *>, RegistryTag>() {
    override val registerableTypeName: String = "registry"
    override val uniqueName: NamespacedKeyOf<RegistryTag> = registryKeyOf(pluginInfo, "registry")

    override fun onEnable() {

    }

    override fun onDisable() {

    }
}