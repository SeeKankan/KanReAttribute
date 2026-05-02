package io.seekankan.github.kanreattribute.attribute.data

import java.util.EnumMap
import java.util.EnumSet

data class FlagContext(
    private val enumFlagMap: MutableMap<Class<out Enum<*>>, MutableSet<Any>> = mutableMapOf(),
    private val objectFlagMap: MutableMap<Class<*>, MutableSet<*>> = mutableMapOf()
) {
    inline fun <reified T: Enum<T>> addEnumFlag(flag: T) {
        addEnumFlag(T::class.java, flag)
    }
    fun <T: Enum<T>> addEnumFlag(type: Class<T>, flag: T) {
        val set = enumFlagMap.getOrPut(type) {
            EnumSet.noneOf(type) as MutableSet<Any>
        }
        set.add(flag)
    }
    inline fun <reified T: Enum<T>> removeEnumFlag(flag: T) {
        removeEnumFlag(T::class.java, flag)
    }
    fun <T: Enum<T>> removeEnumFlag(type: Class<T>, flag: T) {
        enumFlagMap[type]?.remove(flag)
    }
    inline fun <reified T: Enum<T>> hasEnumFlag(flag: T): Boolean {
        return hasEnumFlag(T::class.java, flag)
    }
    fun <T: Enum<T>> hasEnumFlag(type: Class<T>, flag: T): Boolean {
        return enumFlagMap[type]?.contains(flag) ?: false
    }
}