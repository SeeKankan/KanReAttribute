package io.seekankan.github.kanreattribute.gui

import io.seekankan.github.kanreattribute.extensions.toThrowable
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import java.util.ArrayDeque
import java.util.Deque

abstract class AsyncPrivateInventory: BaseInventoryHolder() {

    val pageStack: Deque<Page> = ArrayDeque()

    override fun onInventoryClose(event: InventoryCloseEvent) {
        val exceptionList = mutableListOf<Exception>()
        while (pageStack.isNotEmpty()) {
            val page = pageStack.pollLast()
            try {
                page.onPageClose(event)
            } catch (exception: Exception) {
                exceptionList.add(exception)
            }
        }
        val exception = exceptionList.toThrowable()
        if(exception != null) throw exception
    }

    override fun onInventoryClick(event: InventoryClickEvent) {
        val page = pageStack.peekLast() ?: return
        page.onPageClick(event)
    }

    override fun onInventoryDrag(event: InventoryDragEvent) {
        val page = pageStack.peekLast() ?: return
        page.onPageDrag(event)
    }

    override fun onInventoryOpen(event: InventoryOpenEvent) {
        pageStack.forEach { page ->
            page.onPageOpen(event)
        }
    }
}