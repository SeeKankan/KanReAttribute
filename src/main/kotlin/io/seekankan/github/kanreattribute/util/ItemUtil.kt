package io.seekankan.github.kanreattribute.util

import net.axay.kspigot.items.meta
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

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
//        returns(true) implies (this@isValid != null)
//    }
    return this != null && this.type != Material.AIR && this.amount > 0
}