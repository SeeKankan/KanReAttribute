package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.ConfigHolder
import io.seekankan.github.kanreattribute.coroutines.CoroutineManager
import io.seekankan.github.kanreattribute.coroutines.ScheduleService
import io.seekankan.github.kanreattribute.coroutines.handle.DebounceHandle

class AttributeRefreshDebounceHandle(
    private val configHolder: ConfigHolder,
    private val scheduleService: ScheduleService,
    private val coroutineManager: CoroutineManager
): DebounceHandle(
    coroutineManager
) {
    override suspend fun delayDebounce() {
        scheduleService.delayTicks(configHolder.currentConfig.attribute.attributeRefreshDelay)
    }
}