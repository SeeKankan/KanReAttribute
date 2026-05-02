package io.seekankan.github.kanreattribute.item.itemtype

import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.common.ItemInstanceConfigKey
import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.item.data.ItemSlot
import io.seekankan.github.kanreattribute.item.itemcreate.ItemInstanceConfig
import io.seekankan.github.kanreattribute.util.LifeCycle
import io.seekankan.github.kanreattribute.util.MathUtil
import io.seekankan.github.kanreattribute.util.Named
import org.bukkit.Material

abstract class ItemType : Named<ItemTypeKey>, Comparable<ItemType>, LifeCycle {
    abstract override val uniqueName: ItemTypeKey
    abstract val displayName: String
    abstract val priority: Int
    abstract val material: Material
    abstract val slots: List<ItemSlot>
    abstract val attrMap: AttributeMap
    abstract val introduction: String?
    abstract val lore: List<String>
    abstract val customConfig: Map<String, Any>
    abstract val instanceConfig: Map<ItemInstanceConfigKey, ItemInstanceConfig>

    override fun compareTo(other: ItemType): Int {
//        val num1 = priority.compareTo(other.priority)
//        return if(num1 != 0) num1 else {
//            val num2 = uniqueName.namespace.compareTo(uniqueName.namespace)
//            if(num2 != 0) num2 else uniqueName.key.compareTo(other.uniqueName.key)
//        }
        return MathUtil.compare(
            this,
            priority,
            other,
            other.priority
        )
    }

    override fun onEnable() {

    }

    override fun onDisable() {

    }
//    fun applyAttributes(lastAttributes: AttributeMap) {
//        attrMap.forEach { (key, value) ->
//            lastAttributes.add(key, value)
//        }
//    }
}