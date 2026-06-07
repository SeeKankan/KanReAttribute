package io.seekankan.github.kanreattribute.eventhandle

import io.seekankan.github.kanreattribute.common.EventHandleBehaviorKey
import io.seekankan.github.kanreattribute.registry.AbstractPluginFunctionRegistry
import java.util.logging.Logger

class EventHandleRegistry(
    logger: Logger
): AbstractPluginFunctionRegistry<EventHandleBehaviorKey, EventHandleBehavior<*>>(
    "EventHandleBehavior",
    logger
) {

}