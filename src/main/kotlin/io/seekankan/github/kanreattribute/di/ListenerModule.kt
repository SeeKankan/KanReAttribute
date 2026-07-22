package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.listener.BanShieldListener
import io.seekankan.github.kanreattribute.listener.CacheCleanerListener
import io.seekankan.github.kanreattribute.listener.CachePlayerAttackCooldownListener
import io.seekankan.github.kanreattribute.listener.DamageListener
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.koin.core.component.KoinComponent
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.dsl.onClose

class ListenerModule(
    private val plugin: KanReAttribute,
): PluginModule, KoinComponent {
    override val name: String = "ListenerModule"

    override val koinModule: Module  = module {
        single { BanShieldListener(get()) }.bindAutoReg().unregisterOnClose()
        single { DamageListener(plugin) }.bindAutoReg().unregisterOnClose()

        singleOf(::CacheCleanerListener).bindAutoReg().unregisterOnClose()
        singleOf(::CachePlayerAttackCooldownListener).bindAutoReg().unregisterOnClose()
    }


    override fun onEnable() {
        registerListeners()
    }

    override fun onReload() {

    }

    private fun registerListeners() {
        val listeners = getKoin().getAll<AutoRegistrable>()

        val pluginManager = Bukkit.getPluginManager()

        listeners.forEach {
            if(it is Listener) pluginManager.registerEvents(it, plugin)
        }
    }
}
private fun <T: AutoRegistrable> KoinDefinition<T>.bindAutoReg(): KoinDefinition<T> {
    this.bind(AutoRegistrable::class)
    return this
}
private fun <T: Listener> KoinDefinition<T>.unregisterOnClose(): KoinDefinition<T> {
    this.onClose {
        if(it == null) return@onClose
        HandlerList.unregisterAll(it)
    }
    return this
}