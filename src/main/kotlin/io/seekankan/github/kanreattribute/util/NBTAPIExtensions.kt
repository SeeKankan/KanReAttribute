package io.seekankan.github.kanreattribute.util

import de.tr7zw.nbtapi.NBT
import de.tr7zw.nbtapi.iface.ReadWriteItemNBT
import de.tr7zw.nbtapi.wrapper.NBTProxy
import org.bukkit.inventory.ItemStack

fun <T> modifyNBT(item: ItemStack, function: (ReadWriteItemNBT) -> T): T {
    return NBT.modify(item, function)
}
fun <T, X: NBTProxy> modifyNBT(item: ItemStack, wrapper: Class<X>, function: (X) -> T): T {
    return NBT.modify(item, wrapper, function)
}