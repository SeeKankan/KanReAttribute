package io.seekankan.github.kanreattribute.coroutines.handle

import io.seekankan.github.kanreattribute.coroutines.CoroutinesManager
import io.seekankan.github.kanreattribute.coroutines.ScheduleService
import io.seekankan.github.kanreattribute.coroutines.time.DelayTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.job
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

class DebounceHandle(
    private val delayTimes: DelayTime,
    private val scheduleService: ScheduleService,
    private val coroutinesManager: CoroutinesManager
) {
    private val pendingJobs = ConcurrentHashMap<UUID, Job>()

    fun trigger(uuid: UUID, context: CoroutineContext, action: suspend CoroutineScope.() -> Unit) {
        pendingJobs.compute(uuid) { _, oldJob ->
            oldJob?.cancel("Debounce")

            coroutinesManager.launchIn(context) {
                delayTimes.delayBy(scheduleService)

                try {
                    action(this)
                } finally {
                    pendingJobs.remove(uuid, currentCoroutineContext().job)
                }
            }
        }
    }

    fun cancel(uuid: UUID) {
        pendingJobs.remove(uuid)?.cancel("Cancel by DebounceHandle#cancel(uuid) call.")
    }

}