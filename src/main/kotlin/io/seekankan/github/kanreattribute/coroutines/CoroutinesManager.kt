package io.seekankan.github.kanreattribute.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.util.logging.Logger
import kotlin.coroutines.resume

class CoroutinesManager(
    private val plugin: Plugin,
    private val logger: Logger,
    private val bukkitDispatcher: BukkitDispatcher
) {
    private val rootJob: Job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val scopeSimpleName = this@CoroutinesManager.plugin.javaClass.simpleName
        logger.severe("A Uncaught Coroutine Exception (Scope: $scopeSimpleName")
        throwable.printStackTrace()
    }
    private val scope: CoroutineScope = CoroutineScope(
        rootJob + bukkitDispatcher + Dispatchers.IO + exceptionHandler
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun delayTicks(ticks: Ticks) {
        require(ticks.value >= 0) {
            "Delayed ticks must be non-negative"
        }
        suspendCancellableCoroutine { continuation ->
            val runnable = Runnable { continuation.resume(Unit) }
            val bukkitTask = Bukkit.getScheduler().runTaskLater(plugin, runnable, ticks.value)
            continuation.invokeOnCancellation {
                bukkitTask.cancel()
            }
        }
    }

    fun launchBukkit(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(bukkitDispatcher,block = block)
    }

    fun shutdown() {
        scope.cancel()
        logger.info("CoroutinesManager shutdown.")
    }
}