package io.seekankan.github.kanreattribute.gui

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent

interface PageEventObserver {

    //close
    fun onPageClose(event: InventoryCloseEvent) {}
    //interact
    fun onPageClick(event: InventoryClickEvent) {}
    fun onPageDrag(event: InventoryDragEvent) {}
    //open
    fun onPageOpen(event: InventoryOpenEvent) {}

}