package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.eventhandle.EventHandleBehavior
import io.seekankan.github.kanreattribute.eventhandle.EventHandleRegistry
import io.seekankan.github.kanreattribute.eventhandle.EventHandleSystem
import io.seekankan.github.kanreattribute.eventhandle.impl.DamageEventHandleBehavior
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class EventDataHandleModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "EventDataHandleModule"

    override val koinModule: Module = module {
        singleOf(::EventHandleRegistry)

        singleOf(::EventHandleSystem)

        singleOf(::DamageEventHandleBehavior) bind EventHandleBehavior::class
    }

    override fun onEnable() {
        registerEventHandleBehavior()
    }

    override fun onReload() {

    }


    private fun registerEventHandleBehavior() {
        val eventHandleRegistry = getKoin().get<EventHandleRegistry>()

        val eventHandleBehaviors = getKoin().getAll<EventHandleBehavior<*>>()

        eventHandleBehaviors.forEach { behavior ->
            eventHandleRegistry.registerPersistent(behavior)
        }

    }
}