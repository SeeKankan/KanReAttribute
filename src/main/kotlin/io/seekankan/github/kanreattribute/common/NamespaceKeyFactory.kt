package io.seekankan.github.kanreattribute.common

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.bukkit.NamespacedKey
import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.plugin.Plugin

@JvmInline
value class NamespacedKeyOf<@Suppress("unused") T>(val value: NamespacedKey) {
    companion object {
        fun <T> fromString(string: String): NamespacedKeyOf<T>? {
            return NamespacedKey.fromString(string)?.let { NamespacedKeyOf(it) }
        }
        @JvmStatic
        @JsonCreator
        fun <T> create(string: String): NamespacedKeyOf<T> {
            return fromString(string) ?: throw IllegalArgumentException("Illegal NamespacedKey [$string]")
        }
    }
    override fun toString(): String {
        return value.toString()
    }
}
val NamespacedKeyOf<*>.namespace get() = value.namespace
val NamespacedKeyOf<*>.key get() = value.key


interface ItemTypeTag
interface ItemConditionTag
interface ItemFinderTag
interface ItemCreateHandlerTag

typealias ItemTypeKey = NamespacedKeyOf<ItemTypeTag>
typealias ItemConditionKey = NamespacedKeyOf<ItemConditionTag>
typealias ItemFinderKey = NamespacedKeyOf<ItemFinderTag>
typealias ItemCreateHandlerKey = NamespacedKeyOf<ItemCreateHandlerTag>

//object NamespaceKeyFactory {
//
//}
inline fun <reified T> keyOf(plugin: Plugin, key: String): NamespacedKeyOf<T> {
    return NamespacedKeyOf<T>(NamespacedKey(plugin, key))
}
fun itemTypeKey(namespace: String, key: String): ItemTypeKey {
    return NamespacedKeyOf(NamespacedKey(namespace, key))
}
fun itemTypeKey(plugin: Plugin, key: String): ItemTypeKey {
    return keyOf(plugin, key)
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

@JsonDeserialize(keyUsing = StringOfKeyDeserializer::class)
@JvmInline
value class StringOf<@Suppress("unused") T>(val value: String) {
    companion object {
        fun <T> fromString(string: String): StringOf<T> {
            return StringOf(string)
        }
        @JvmStatic
        @JsonCreator
        fun <T> create(string: String): StringOf<T> {
            return fromString(string)
        }
    }
    override fun toString(): String {
        return value
    }
}

interface ItemInstanceConfigTag

typealias ItemInstanceConfigKey = StringOf<ItemInstanceConfigTag>

val ITEM_INSTANCE_CONFIG_KEY_DEFAULT = itemInstConfigKey("default")
fun itemInstConfigKey(type: String): ItemInstanceConfigKey {
    return ItemInstanceConfigKey(type)
}