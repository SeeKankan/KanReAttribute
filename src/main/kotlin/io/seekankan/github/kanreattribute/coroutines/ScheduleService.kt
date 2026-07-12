package io.seekankan.github.kanreattribute.coroutines

import io.seekankan.github.kanreattribute.coroutines.annotation.Delayable
import io.seekankan.github.kanreattribute.coroutines.time.Milliseconds
import io.seekankan.github.kanreattribute.coroutines.time.Ticks
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import kotlin.coroutines.resume
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


class ScheduleService(
    private val plugin: Plugin
) {
    @Delayable
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

    @Delayable
    suspend fun delayMillis(millis: Milliseconds) {
        delay(millis.value.milliseconds)
    }

    @Delayable
    suspend fun delayDuration(duration: Duration) {
        delay(duration)
    }
}