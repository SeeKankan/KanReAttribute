package io.seekankan.github.kanreattribute.util

interface Configurable<T> {
    fun createDefaultConfig(): T
    fun writeConfig(config: T)
    fun fetchConfig(): T
}