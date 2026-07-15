package io.seekankan.github.kanreattribute.gui

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

abstract class BaseInventoryHolder: InventoryHolder {
    val inv: Inventory = createInventory()

    protected abstract fun createInventory(): Inventory
    override fun getInventory(): Inventory {
        return inv
    }
}