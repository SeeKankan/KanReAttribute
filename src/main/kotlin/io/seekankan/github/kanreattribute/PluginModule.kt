package io.seekankan.github.kanreattribute

import org.koin.core.module.Module

interface PluginModule {
    val name: String

    val koinModule: Module

    fun onEnable()
    fun onReload()
}