package io.seekankan.github.kanreattribute.coroutines.time

import io.seekankan.github.kanreattribute.coroutines.ScheduleService
import io.seekankan.github.kanreattribute.coroutines.annotation.Delayable

interface DelayTime {
    @Delayable
    suspend fun delayBy(scheduleService: ScheduleService)
}