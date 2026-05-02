package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.PluginModule
import org.koin.core.module.Module
import org.koin.dsl.module

class ModuleTemplate: PluginModule { //Do not use.Just copy this.
    override val name: String = "TODO"

    override val koinModule: Module = module {

    }

    override fun onEnable() {}
    override fun onReload() {

    }


}