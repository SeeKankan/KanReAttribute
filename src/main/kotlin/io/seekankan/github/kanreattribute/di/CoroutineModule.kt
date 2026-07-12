package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.coroutines.BukkitDispatcher
import io.seekankan.github.kanreattribute.coroutines.CoroutineManager
import io.seekankan.github.kanreattribute.coroutines.ScheduleService
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.dsl.onClose

class CoroutineModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "CoroutineModule"

    override val koinModule: Module = module {
        singleOf(::BukkitDispatcher)

        singleOf(::ScheduleService)

        singleOf(::CoroutineManager).onClose {
            it?.shutdown()
        }

    }

    override fun onEnable() {

    }

    override fun onReload() {

    }

}