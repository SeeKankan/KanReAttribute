package io.seekankan.github.kanreattribute.coroutines.handle

import io.seekankan.github.kanreattribute.coroutines.CoroutineManager
import io.seekankan.github.kanreattribute.coroutines.annotation.Delayable
import io.seekankan.github.kanreattribute.coroutines.annotation.LaunchesCoroutine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.job
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

abstract class DebounceHandle(
    private val coroutinesManager: CoroutineManager
) {
    private val pendingJobs = ConcurrentHashMap<UUID, Job>()

    @Delayable
    abstract suspend fun delayDebounce()

    @LaunchesCoroutine
    fun trigger(uuid: UUID, context: CoroutineContext, action: suspend CoroutineScope.() -> Unit) {
        val newJob = coroutinesManager.launchIn(context) {
            try {
                delayDebounce()
                if(pendingJobs[uuid] != this.coroutineContext.job) return@launchIn
                action(this)
            } finally {
                pendingJobs.remove(uuid, this.coroutineContext.job)
            }
        }
        pendingJobs.put(uuid, newJob)?.cancel("Re-triggered debounce for player $uuid")
    }

    fun cancel(uuid: UUID) {
        pendingJobs.remove(uuid)?.cancel("Cancel by DebounceHandle#cancel(uuid#$uuid) call.")
    }

}