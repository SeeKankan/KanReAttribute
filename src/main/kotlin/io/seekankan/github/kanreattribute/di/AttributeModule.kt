package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.attribute.AttributeCalculator
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.SubAttribute
import io.seekankan.github.kanreattribute.attribute.impl.attributecalculator.BaseAttributeCalculator
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.Damage
import io.seekankan.github.kanreattribute.attribute.impl.attributecalculator.ItemAttributeCalculator
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.CritChance
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.CritDamage
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.Strength
import io.seekankan.github.kanreattribute.attribute.listener.EntityAttributeRefresher
import org.bukkit.event.HandlerList
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.onClose

class AttributeModule(
    private val plugin: KanReAttribute
): PluginModule, KoinComponent {
    override val name: String = "AttributeModule"

    private val attributeManager: AttributeManager by inject()
    private val attributeRefresher: EntityAttributeRefresher by inject()

    override val koinModule: Module = module {
        single<AttributeManager>(createdAtStart = true) {
            AttributeManager(plugin)
        }.onClose {
            it?.unregisterListener()
        }

        singleOf(::EntityAttributeRefresher).onClose {
            if (it != null) {
                HandlerList.unregisterAll(it)
            }
        }

        singleOf(::BaseAttributeCalculator) bind AttributeCalculator::class
        singleOf(::ItemAttributeCalculator) bind AttributeCalculator::class

        singleOf(::Damage) bind SubAttribute::class
        singleOf(::Strength) bind SubAttribute::class
        singleOf(::CritChance) bind SubAttribute::class
        singleOf(::CritDamage) bind SubAttribute::class
    }

    override fun onEnable() {
        attributeManager.registerListener()
        plugin.server.pluginManager.registerEvents(attributeRefresher, plugin)
        registerAttributes(attributeManager)
    }

    override fun onReload() {
        attributeManager.reloadAttributes()
    }


    private fun registerAttributes(manager: AttributeManager) {
//        val attributeCalculators = arrayOf(
//            BaseAttributeCalculator(manager),
//            ItemAttributeCalculator(get()),
//        )
        val attributeCalculators = getKoin().getAll<AttributeCalculator>()
//        val subAttributes = arrayOf(
//            Damage(plugin)
//        )
        val subAttributes= getKoin().getAll<SubAttribute>()
        attributeCalculators.forEach {
            manager.attributeCalculatorRegistry.register(it)
        }
        subAttributes.forEach {
            manager.subAttributeRegistry.register(it)
        }


    }
}
