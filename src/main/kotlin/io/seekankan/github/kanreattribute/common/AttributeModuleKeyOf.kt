package io.seekankan.github.kanreattribute.common

import io.seekankan.github.kanreattribute.PluginInfo

interface EffectApplierTag

typealias EffectApplierKey = NamespacedKeyOf<EffectApplierTag>

fun effectApplierKeyOf(pluginInfo: PluginInfo, key: String): EffectApplierKey {
    return effectApplierKeyOf(pluginInfo.snakeCaseName, key)
}
fun effectApplierKeyOf(namespace: String, key: String): EffectApplierKey {
    return keyOf(namespace, key)
}