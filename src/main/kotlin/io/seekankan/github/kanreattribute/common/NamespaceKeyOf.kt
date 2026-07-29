package io.seekankan.github.kanreattribute.common

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.seekankan.github.kanreattribute.jackson.StringOfKeyDeserializer
import org.bukkit.NamespacedKey
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
inline val NamespacedKeyOf<*>.namespace get() = value.namespace
inline val NamespacedKeyOf<*>.key get() = value.key


inline fun <reified T> keyOf(plugin: Plugin, key: String): NamespacedKeyOf<T> {
    return NamespacedKeyOf<T>(NamespacedKey(plugin, key))
}

inline fun <reified T> keyOf(namespace: String, key: String): NamespacedKeyOf<T> {
//    return NamespacedKeyOf(
//        NamespacedKey.fromString("$namespace:$key") ?: throw IllegalArgumentException(
//            "Invalid namespaceKey: '$namespace:$key'"
//        ))
    return NamespacedKeyOf(
        NamespacedKey(namespace, key)
    )
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

