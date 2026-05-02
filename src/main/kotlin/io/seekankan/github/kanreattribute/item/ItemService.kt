package io.seekankan.github.kanreattribute.item

import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.item.data.LivingEntityInventoryData
import io.seekankan.github.kanreattribute.item.manager.ItemConditionManager
import io.seekankan.github.kanreattribute.item.manager.ItemFinderManager
import io.seekankan.github.kanreattribute.item.manager.ItemTypeManager
import org.bukkit.entity.LivingEntity

class ItemService(
    private val itemTypeManager: ItemTypeManager,
    private val itemFinderManager: ItemFinderManager,
    private val itemConditionManager: ItemConditionManager
) {
    fun getValidInventoryData(livingEntity: LivingEntity): LivingEntityInventoryData {
        val data = itemFinderManager.findInventoryItems(livingEntity)
        itemConditionManager.filterInventoryItems(livingEntity, data)
        return data
    }
    fun addInAttributeMap(livingEntity: LivingEntity, invData: LivingEntityInventoryData, attributeMap: AttributeMap) {
//        invData.invDataEnumMap.forEach { (slot, stacks) ->
//            stacks.forEach { stack ->
//                val itemType = itemTypeManager.getItemType(stack) ?: return@forEach
//                itemType.applyAttributes(attributeMap)
//            }
//        }
//        val values = invData.invDataEnumMap.values
//        val itemStacks = values.flatten()
//        val flatMap = itemStacks.flatMap {
//            val itemType = itemTypeManager.getItemType(it)
//            itemType?.attrMap?.entries ?: emptyList()
//        }
//        val groupBy = flatMap.groupingBy {
//            it.key
//        }
//        val folded = groupBy.fold(0.0) { acc, entry ->
//            acc + entry.value
//        }
//        folded.forEach { (attributeType, value) ->
//            attributeMap.add(attributeType, value)
//        }


//        invData.invDataEnumMap.values
//            .flatten()
//            .flatMap {
//                val itemType = itemTypeManager.getItemType(it)
//                itemType?.attrMap?.entries ?: emptyList()
//            }.groupingBy {
//                it.key
//            }.fold(0.0) { acc, entry ->
//                acc + entry.value
//            }.forEach { (attributeType, value) ->
//                attributeMap.add(attributeType, value)
//            }

        invData.invDataEnumMap.values
            .flatMap { it }
            .mapNotNull {
                it.itemType
            }
            .flatMap {
                it.attrMap.entries
            }
            .groupingBy {
                it.key
            }
            .fold(0.0) { acc, entry ->
                acc + entry.value
            }.forEach { (attributeType, value) ->
                attributeMap.add(attributeType, value)
            }


    }
}