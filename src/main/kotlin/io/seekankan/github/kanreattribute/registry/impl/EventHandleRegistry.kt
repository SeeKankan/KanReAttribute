package io.seekankan.github.kanreattribute.registry.impl

import io.seekankan.github.kanreattribute.PluginInfo
import io.seekankan.github.kanreattribute.common.EventHandleBehaviorTag
import io.seekankan.github.kanreattribute.common.NamespacedKeyOf
import io.seekankan.github.kanreattribute.common.RegistryTag
import io.seekankan.github.kanreattribute.common.registryKeyOf
import io.seekankan.github.kanreattribute.eventhandle.EventHandleBehavior
import io.seekankan.github.kanreattribute.registry.CopyOnWriteRegistry

class EventHandleRegistry(
    private val pluginInfo: PluginInfo
): CopyOnWriteRegistry<EventHandleBehavior<*>, EventHandleBehaviorTag>() {
    override val registerableTypeName: String = "EventHandle"
    override val uniqueName: NamespacedKeyOf<RegistryTag> = registryKeyOf(pluginInfo, "event_handle")

}