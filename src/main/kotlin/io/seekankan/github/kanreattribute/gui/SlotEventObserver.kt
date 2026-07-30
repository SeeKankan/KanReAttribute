package io.seekankan.github.kanreattribute.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent

interface SlotEventObserver {

    //close
    fun onSlotClose(event: InventoryCloseEvent) {}
    //interact
    fun onSlotClick(event: InventoryClickEvent) {}
    fun onSlotDrag(event: InventoryDragEvent) {}
    //open
    fun onSlotOpen(event: InventoryOpenEvent) {}

}