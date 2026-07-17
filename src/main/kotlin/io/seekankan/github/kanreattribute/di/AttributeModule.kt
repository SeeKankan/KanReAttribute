package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.attribute.AttributeService
import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.registry.impl.AttributeCalculatorRegistry
import io.seekankan.github.kanreattribute.attribute.attributecalculator.AttributeCalculator
import io.seekankan.github.kanreattribute.attribute.AttributeManager
import io.seekankan.github.kanreattribute.attribute.AttributeRefreshDebounceHandle
import io.seekankan.github.kanreattribute.registry.impl.EffectApplierRegistry
import io.seekankan.github.kanreattribute.registry.impl.SubAttributeRegistry
import io.seekankan.github.kanreattribute.attribute.effectapplier.EffectApplier
import io.seekankan.github.kanreattribute.attribute.subattribute.SubAttribute
import io.seekankan.github.kanreattribute.attribute.impl.attributecalculator.BaseAttributeCalculator
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.Damage
import io.seekankan.github.kanreattribute.attribute.impl.attributecalculator.ItemAttributeCalculator
import io.seekankan.github.kanreattribute.attribute.impl.effectapplier.FinalDamageHologramSpawner
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.AttackSpeed
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.CritChance
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.CritDamage
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.LifeSteal
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.LifeStealChance
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.attacker.Strength
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.defence.Defense
import io.seekankan.github.kanreattribute.attribute.impl.subattribute.defence.ExtraHealth
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


//        single<AttributeManager> {
//            AttributeManager(plugin)
//        }.onClose {
//            it?.unregisterListener()
//        }

        singleOf(::AttributeRefreshDebounceHandle)

        singleOf(::AttributeManager).onClose {
            it?.unregisterListener()
        }

        singleOf(::AttributeService)

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
        singleOf(::LifeStealChance) bind SubAttribute::class
        singleOf(::LifeSteal) bind SubAttribute::class
        singleOf(::AttackSpeed) bind SubAttribute::class

        singleOf(::ExtraHealth) bind SubAttribute::class
        singleOf(::Defense) bind SubAttribute::class

        singleOf(::FinalDamageHologramSpawner) bind EffectApplier::class
    }

    override fun onEnable() {
        attributeManager.registerListener()
        plugin.server.pluginManager.registerEvents(attributeRefresher, plugin)
        registerAttributes(attributeManager)
    }

    override fun onReload() {

    }


    private fun registerAttributes(manager: AttributeManager) {
//        val attributeCalculators = arrayOf(
//            BaseAttributeCalculator(manager),
//            ItemAttributeCalculator(get()),
//        )
        val attributeCalculatorRegistry = getKoin().get<AttributeCalculatorRegistry>()
        val subAttributeRegistry = getKoin().get<SubAttributeRegistry>()
        val effectApplierRegistry = getKoin().get<EffectApplierRegistry>()

        val attributeCalculators = getKoin().getAll<AttributeCalculator>()
        val subAttributes = getKoin().getAll<SubAttribute>()
        val effectAppliers = getKoin().getAll<EffectApplier>()

//        attributeCalculators.forEach {
//            attributeCalculatorRegistry.register(it)
//        }
//        subAttributes.forEach {
//            subAttributeRegistry.register(it)
//        }
//        effectAppliers.forEach {
//            effectApplierRegistry.registerPersistent(it)
//        }
        attributeCalculatorRegistry.registerAll(attributeCalculators)
        subAttributeRegistry.registerAll(subAttributes)
        effectApplierRegistry.registerAll(effectAppliers)

    }
}
