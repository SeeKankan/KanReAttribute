package io.seekankan.github.kanreattribute.common

import io.seekankan.github.kanreattribute.PluginInfo

interface EventHandleBehaviorTag

typealias EventHandleBehaviorKey = NamespacedKeyOf<EffectApplierTag>

fun eventHandleBehaviorKeyOf(pluginInfo: PluginInfo, key: String): EffectApplierKey {
    return effectApplierKeyOf(pluginInfo.snakeCaseName, key)
}
fun eventHandleBehaviorKeyOf(namespace: String, key: String): EffectApplierKey {
    return keyOf(namespace, key)
}