package io.seekankan.github.kanreattribute.di

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.PluginModule
import io.seekankan.github.kanreattribute.listener.ListenerBanShield
import io.seekankan.github.kanreattribute.listener.ListenerCacheCleaner
import io.seekankan.github.kanreattribute.listener.ListenerCachePlayerAttackCooldown
import io.seekankan.github.kanreattribute.listener.ListenerDamage
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
        single { ListenerBanShield(get()) }.bindAutoReg().unregisterOnClose()
        single { ListenerDamage(plugin) }.bindAutoReg().unregisterOnClose()

        singleOf(::ListenerCacheCleaner).bindAutoReg().unregisterOnClose()
        singleOf(::ListenerCachePlayerAttackCooldown).bindAutoReg().unregisterOnClose()
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