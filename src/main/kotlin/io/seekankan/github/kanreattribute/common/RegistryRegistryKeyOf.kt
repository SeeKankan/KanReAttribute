package io.seekankan.github.kanreattribute.common

import io.seekankan.github.kanreattribute.PluginInfo

interface RegistryTag

typealias RegistryKey = NamespacedKeyOf<RegistryTag>

fun registryKeyOf(pluginInfo: PluginInfo, key: String): RegistryKey {
    return registryKeyOf(pluginInfo.snakeCaseName, key)
}
fun registryKeyOf(namespace: String, key: String): RegistryKey {
    return keyOf(namespace, key)
}