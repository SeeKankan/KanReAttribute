package io.seekankan.github.kanreattribute.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent

interface InventoryEventObserver {

    //close
    fun onInventoryClose(event: InventoryCloseEvent) {}
    //interact
    fun onInventoryClick(event: InventoryClickEvent) {}
    fun onInventoryDrag(event: InventoryDragEvent) {}
    //open
    fun onInventoryOpen(event: InventoryOpenEvent) {}

}