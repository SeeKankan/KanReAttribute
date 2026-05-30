package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.holograms.HologramManager
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class HologramModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "HologramModule"

    override val koinModule: Module = module {

        singleOf(::HologramManager)

    }

    override fun onEnable() {

    }

    override fun onReload() {

    }

}