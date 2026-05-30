package io.seekankan.github.kanreattribute.util

import net.axay.kspigot.items.meta
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

inline fun <reified T, P>
        ItemStack.getItemData(key: NamespacedKey, pdcType: PersistentDataType<P,T>): T? {
    val meta = this.itemMeta ?: return null
    val pdc = meta.persistentDataContainer
    return pdc.get(key, pdcType)
}
inline fun <reified T, P>
        ItemStack.setItemData(key: NamespacedKey, pdcType: PersistentDataType<P,T>, value: T) {
    this.meta {
        val pdc = persistentDataContainer
        pdc.set(key, pdcType, value!!)
    }
}
/*@ExperimentalContracts*/
fun ItemStack?.isValid(): Boolean {
//    contract {
//        returns(true) implies (this@isHologramValid != null)
//    }
    return this != null && this.type != Material.AIR && this.amount > 0
}

var ItemStack.gsonDisplayName: String
    get() {
        return modifyNBT(this, ItemStackInterface::class.java) { proxy ->
            return@modifyNBT proxy.displayInterface.gsonDisplayName
        }
    }
    set(value) {
        modifyNBT(this, ItemStackInterface::class.java) { proxy ->
            proxy.displayInterface.gsonDisplayName = value
        }
    }
var ItemStack.gsonLore: List<String>?
    get() {
        return modifyNBT(this) { nbt ->
            val display = nbt.getCompound("display") ?: return@modifyNBT null
            if(!display.hasTag("Lore")) return@modifyNBT null
            val nbtLoreList = display.getStringList("Lore") ?: return@modifyNBT null
            return@modifyNBT nbtLoreList.toList()
        }
    }
    set(value) {
        if(value == null) {
            this.meta {
                lore = null
            }
            return
        }
        modifyNBT(this) { nbt ->
            val displayNBT = nbt.getOrCreateCompound("display")
            val nbtLoreList = displayNBT.getStringList("Lore")
            nbtLoreList.clear()

            nbtLoreList.addAll(value)
        }
    }