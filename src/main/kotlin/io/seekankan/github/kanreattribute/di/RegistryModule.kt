package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry
import io.seekankan.github.kanreattribute.registry.RegistryRegistry
import io.seekankan.github.kanreattribute.registry.impl.AttributeCalculatorRegistry
import io.seekankan.github.kanreattribute.registry.impl.EffectApplierRegistry
import io.seekankan.github.kanreattribute.registry.impl.EventHandleRegistry
import io.seekankan.github.kanreattribute.registry.impl.ItemConditionRegistry
import io.seekankan.github.kanreattribute.registry.impl.ItemCreateHandlerRegistry
import io.seekankan.github.kanreattribute.registry.impl.ItemFinderRegistry
import io.seekankan.github.kanreattribute.registry.impl.ItemTypeRegistry
import io.seekankan.github.kanreattribute.registry.impl.SubAttributeRegistry
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class RegistryModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "RegistryModule"

    override val koinModule: Module = module {

        singleOf(::RegistryRegistry) bind CopyOnWriteRegistry::class

        singleOf(::ItemTypeRegistry) bind CopyOnWriteRegistry::class
        singleOf(::ItemFinderRegistry) bind CopyOnWriteRegistry::class
        singleOf(::ItemConditionRegistry) bind CopyOnWriteRegistry::class
        singleOf(::ItemCreateHandlerRegistry) bind CopyOnWriteRegistry::class

        singleOf(::AttributeCalculatorRegistry) bind CopyOnWriteRegistry::class
        singleOf(::SubAttributeRegistry) bind CopyOnWriteRegistry::class
        singleOf(::EffectApplierRegistry) bind CopyOnWriteRegistry::class

        singleOf(::EventHandleRegistry) bind CopyOnWriteRegistry::class
    }

    override fun onEnable() {
        val koin = getKoin()
        val registryRegistry = koin.get<RegistryRegistry>()
        val registers = koin.getAll<CopyOnWriteRegistry<*, *>>()

        registryRegistry.registerAll(registers)
    }
    override fun onReload() {
        val koin = getKoin()
        val registryRegistry = koin.get<RegistryRegistry>()

        registryRegistry.reloadAll()
    }


}