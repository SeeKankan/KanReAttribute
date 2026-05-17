package io.seekankan.github.kanreattribute.item.itemtype

//abstract class ItemType : Named<ItemTypeKey>, Comparable<ItemType>, LifeCycle {
//    abstract override val uniqueName: ItemTypeKey
//    abstract val displayName: String
//    abstract val priority: Int
//    abstract val material: Material
//    abstract val category: ItemCategory
//    abstract val slots: List<ItemSlot>
//    abstract val attrMap: AttributeMap
//    abstract val introduction: String?
//    abstract val lore: List<String>
//    abstract val customConfig: Map<String, Any>
//    abstract val instanceConfig: Map<ItemInstanceConfigKey, ItemInstanceConfig>
//
//    override fun compareTo(other: ItemType): Int {
//        return MathUtil.compare(
//            this,
//            priority,
//            other,
//            other.priority
//        )
//    }
//
//    override fun onEnable() {
//
//    }
//
//    override fun onDisable() {
//
//    }
//}