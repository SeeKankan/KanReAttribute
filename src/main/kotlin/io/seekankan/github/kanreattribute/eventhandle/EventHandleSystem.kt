package io.seekankan.github.kanreattribute.eventhandle

import io.seekankan.github.kanreattribute.data.EventData
import java.util.logging.Logger

class EventHandleSystem(
    private val logger: Logger,
    private val eventHandleRegistry: EventHandleRegistry
) {
    fun handleEventData(eventData: EventData) {
        val eventDataClass = eventData.javaClass

        var isHandledEventData = false
        eventHandleRegistry.pipeLineView.forEach { behavior ->
            val castedBehavior = behavior?.castOrNull(eventDataClass) ?: return@forEach
            castedBehavior.handleEventData(eventData)
            isHandledEventData = true
        }
        if(!isHandledEventData) logger.warning("No EventDataBehavior found for EventData: $eventData")
    }
}