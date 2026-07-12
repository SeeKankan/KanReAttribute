package io.seekankan.github.kanreattribute.coroutines

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.bukkit.plugin.Plugin
import java.util.logging.Logger
import kotlin.coroutines.CoroutineContext

class CoroutineManager(
    private val plugin: Plugin,
    private val logger: Logger,
    private val bukkitDispatcher: BukkitDispatcher
) {
    private val rootJob: Job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val scopeSimpleName = this@CoroutineManager.plugin.javaClass.simpleName
        logger.severe("A Uncaught Coroutine Exception (Scope: $scopeSimpleName")
        throwable.printStackTrace()
    }
    private val scope: CoroutineScope = CoroutineScope(
        rootJob + bukkitDispatcher + Dispatchers.IO + exceptionHandler
    )

    fun launchBukkit(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(bukkitDispatcher,block = block)
    }
    fun launchIn(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(context, block = block)
    }

    fun shutdown() {
        scope.cancel("CoroutineManager shutdown")
        logger.info("CoroutineManager shutdown.")
    }
}