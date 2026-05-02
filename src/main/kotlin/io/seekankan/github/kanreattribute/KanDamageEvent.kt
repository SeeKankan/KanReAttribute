package io.seekankan.github.kanreattribute

import io.seekankan.github.kanreattribute.attribute.data.EntityDamageEventData
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class KanDamageEvent(
    val damageData: EntityDamageEventData
): Event() {
    companion object {
        @JvmField
        val handlers = HandlerList()

        @JvmStatic
        @Suppress("UNUSED")
        fun getHandlerList(): HandlerList = handlers
    }
    override fun getHandlers(): HandlerList {
        return KanDamageEvent.handlers
    }

}