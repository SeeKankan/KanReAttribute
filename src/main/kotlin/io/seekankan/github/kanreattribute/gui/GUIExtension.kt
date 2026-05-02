package io.seekankan.github.kanreattribute.gui

import com.sun.org.apache.xalan.internal.lib.ExsltStrings.padding
import net.axay.kspigot.gui.ForEveryInventory
import net.axay.kspigot.gui.ForInventory
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.InventorySlot
import net.axay.kspigot.gui.InventorySlotCompound
import net.axay.kspigot.gui.SingleInventorySlot
import net.axay.kspigot.gui.Slots

fun Slots.of(slot: Int): SingleInventorySlot<ForEveryInventory> {
    return SingleInventorySlot(slot / 9 + 1,slot % 9 + 1)
}
fun <T : ForInventory> GUIType<T>.lastSlot() : SingleInventorySlot<T> {
    return SingleInventorySlot(dimensions.width, dimensions.height)
}

object KanSlots {
    val Center = InventoryCenterSlots<ForEveryInventory>(1)
}
class InventoryCenterSlots<T : ForInventory> internal constructor(
    val padding: Int
): InventorySlotCompound<T> {
    override fun withInvType(invType: GUIType<T>) = HashSet<InventorySlot>().apply {
        val dimensions = invType.dimensions


        for (row in (1+padding) until dimensions.height - padding) {
            for (col in (1+padding) until dimensions.width - padding) {
                this += InventorySlot(row, col)
            }
        }
    }
}