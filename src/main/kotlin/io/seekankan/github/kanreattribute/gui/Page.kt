package io.seekankan.github.kanreattribute.gui

import io.seekankan.github.kanreattribute.extensions.toThrowable
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent

abstract class Page: PageEventObserver {

    abstract val slotList: List<Slot>

    //close
    override fun onPageClose(event: InventoryCloseEvent) {
        val exceptionList = mutableListOf<Exception>()
        slotList.forEach { slot ->
            try {
                slot.onSlotClose(event)
            } catch (exception: Exception) {
                exceptionList.add(exception)
            }
        }
        val exception = exceptionList.toThrowable()
        if(exception != null) throw exception
    }
    //interact
    override fun onPageClick(event: InventoryClickEvent) {
        val slotLocation = event.slot
        val slot = slotList.getOrNull(slotLocation) ?: return
        slot.onSlotClick(event)
    }
    override fun onPageDrag(event: InventoryDragEvent) {
        val slotLocations = event.inventorySlots
        slotLocations.forEach { slotLocation ->
            val slot = slotList.getOrNull(slotLocation) ?: return@forEach
            slot.onSlotDrag(event)
        }
    }
    //open
    override fun onPageOpen(event: InventoryOpenEvent) {
        slotList.forEach { slot ->
            slot.onSlotOpen(event)
        }
    }

}