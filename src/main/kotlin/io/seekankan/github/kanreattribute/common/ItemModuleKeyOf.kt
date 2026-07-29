package io.seekankan.github.kanreattribute.common

import io.seekankan.github.kanreattribute.PluginInfo
import org.bukkit.NamespacedKey
import org.bukkit.plugin.Plugin

interface ItemTypeTag
interface ItemKindDataTag
interface ItemConditionTag
interface ItemFinderTag
interface ItemCreateHandlerTag

typealias ItemTypeKey = NamespacedKeyOf<ItemTypeTag>
typealias ItemKindDataKey = NamespacedKeyOf<ItemKindDataTag>
typealias ItemConditionKey = NamespacedKeyOf<ItemConditionTag>
typealias ItemFinderKey = NamespacedKeyOf<ItemFinderTag>
typealias ItemCreateHandlerKey = NamespacedKeyOf<ItemCreateHandlerTag>

fun itemTypeKey(namespace: String, key: String): ItemTypeKey {
//    return NamespacedKeyOf(NamespacedKey(namespace, key))
    return NamespacedKeyOf(
        NamespacedKey.fromString("$namespace:$key") ?: throw IllegalArgumentException(
        "Invalid namespaceKey: '$namespace:$key'"
    ))
}

fun itemTypeKey(plugin: Plugin, key: String): ItemTypeKey {
    return keyOf(plugin, key)
}

fun itemKindDataKey(pluginInfo: PluginInfo, key: String): ItemKindDataKey {
    return itemKindDataKey(pluginInfo.snakeCaseName, key)
}

fun itemKindDataKey(namespace: String, key: String): ItemKindDataKey {
//    return NamespacedKeyOf(NamespacedKey(namespace, key))
    return NamespacedKeyOf(NamespacedKey(namespace, key))
}

fun itemConditionKey(plugin: Plugin, key: String): ItemConditionKey {
    return keyOf(plugin, key)
}

fun itemFinderKey(plugin: Plugin, key: String): ItemFinderKey {
    return keyOf(plugin, key)
}

fun itemCreateHandlerKey(plugin: Plugin, key: String): ItemCreateHandlerKey {
    return keyOf(plugin, key)
}

interface ItemInstanceConfigTag

typealias ItemInstanceConfigKey = StringOf<ItemInstanceConfigTag>

val ITEM_INSTANCE_CONFIG_KEY_DEFAULT = itemInstConfigKey("default")
fun itemInstConfigKey(type: String): ItemInstanceConfigKey {
    return ItemInstanceConfigKey(type)
}