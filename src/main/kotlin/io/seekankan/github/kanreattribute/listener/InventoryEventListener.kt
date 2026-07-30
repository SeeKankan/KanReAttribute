package io.seekankan.github.kanreattribute.listener

import io.seekankan.github.kanreattribute.di.AutoRegistrable
import io.seekankan.github.kanreattribute.gui.InventoryEventObserver
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class InventoryEventListener: Listener, AutoRegistrable {

    //close
    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val inventoryHolder = event.inventory.getHolder(false) ?: return
        if(inventoryHolder is InventoryEventObserver) {
            inventoryHolder.onInventoryClose(event)
        }
    }
    //interact
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val inventoryHolder = event.inventory.getHolder(false) ?: return
        if(inventoryHolder is InventoryEventObserver) {
            inventoryHolder.onInventoryClick(event)
        }
    }
    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent) {
        val inventoryHolder = event.inventory.getHolder(false) ?: return
        if(inventoryHolder is InventoryEventObserver) {
            inventoryHolder.onInventoryDrag(event)
        }
    }
    //open
    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        val inventoryHolder = event.inventory.getHolder(false) ?: return
        if(inventoryHolder is InventoryEventObserver) {
            inventoryHolder.onInventoryOpen(event)
        }
    }

}