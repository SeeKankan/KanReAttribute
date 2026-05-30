package io.seekankan.github.kanreattribute.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Runnable
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import kotlin.coroutines.CoroutineContext

class BukkitDispatcher(
    private val plugin: Plugin
): CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if(Bukkit.isPrimaryThread()) {
            block.run()
        } else {
            Bukkit.getScheduler().runTask(plugin, block)
        }
    }

}