package io.seekankan.github.kanreattribute.common

import io.seekankan.github.kanreattribute.PluginInfo

interface EventHandleBehaviorTag

typealias EventHandleBehaviorKey = NamespacedKeyOf<EventHandleBehaviorTag>

fun eventHandleBehaviorKeyOf(pluginInfo: PluginInfo, key: String): EventHandleBehaviorKey {
    return eventHandleBehaviorKeyOf(pluginInfo.snakeCaseName, key)
}
fun eventHandleBehaviorKeyOf(namespace: String, key: String): EventHandleBehaviorKey {
    return keyOf(namespace, key)
}