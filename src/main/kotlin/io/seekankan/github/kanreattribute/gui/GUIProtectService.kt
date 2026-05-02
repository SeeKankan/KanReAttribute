package io.seekankan.github.kanreattribute.gui

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory

class GUIProtectService: Listener {
    private val protectedInventories = mutableSetOf<Inventory>()

    fun add(inv: Inventory) {
        protectedInventories.add(inv)
    }
    fun remove(inv: Inventory) {
        protectedInventories.remove(inv)
    }
    fun contains(inv: Inventory): Boolean {
        return inv in protectedInventories
    }
    fun isEmpty(): Boolean {
        return protectedInventories.isEmpty()
    }
    fun clearAllInventories() {
        protectedInventories.clear()
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun autoRemove(event: InventoryCloseEvent) {
        protectedInventories.remove(event.inventory)
    }
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    fun protectItem(event: InventoryClickEvent) {
        if(event.inventory !in protectedInventories) return

        event.isCancelled = true
    }
}