package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.data.EventData
import io.seekankan.github.kanreattribute.util.EntityDataCache
import org.bukkit.entity.LivingEntity
import org.bukkit.event.HandlerList
import org.bukkit.metadata.FixedMetadataValue

class AttributeManager constructor(private val plugin: KanReAttribute) {
    companion object {
        const val ATTRIBUTE_CACHE_KEY = "kanreattribute_attribute_cache"
    }
    private val cache = EntityDataCache<LivingEntity, AttributeMap>()
    private lateinit var cacheListener: EntityDataCache.CacheListener

    val attributeCalculatorRegistry = AttributeCalculatorRegistry(plugin)

    val subAttributeRegistry = SubAttributeRegistry(plugin)

//    val livingEntityAttributeCache = hashMapOf<UUID, AttributeMap>()
    fun registerListener() {
        cacheListener = EntityDataCache.CacheListener(cache)
        plugin.server.pluginManager.registerEvents(cacheListener, plugin)
    }
    fun unregisterListener() {
        if(::cacheListener.isInitialized) {
            HandlerList.unregisterAll(cacheListener)
        }
    }

    fun computeLivingEntityAttribute(entity: LivingEntity): AttributeMap {
        val entityAttributeMap = AttributeMap()
        attributeCalculatorRegistry.pipeLineView.forEach { calculator ->
            calculator.calculate(entity, entityAttributeMap)
        }
        subAttributeRegistry.pipeLineView.forEach { subAttribute ->
            subAttribute.onUpdate(entity, entityAttributeMap.getOrDefault(subAttribute.uniqueName, 0.0), entityAttributeMap)
        }
        return entityAttributeMap
    }
    fun getLivingEntityAttribute(entity: LivingEntity): AttributeMap {
//        if(!entity.hasMetadata(ATTRIBUTE_CACHE_KEY)) {
//            val attrMap = computeLivingEntityAttribute(entity)
//            entity.setMetadata(ATTRIBUTE_CACHE_KEY, FixedMetadataValue(plugin, attrMap))
//            return attrMap
//        }
//        return entity.getMetadata(ATTRIBUTE_CACHE_KEY)[0].value() as AttributeMap
        return cache.getOrCompute(entity) {
            computeLivingEntityAttribute(entity)
        }

    }
    fun refreshLivingEntityAttribute(entity: LivingEntity): AttributeMap {
//        entity.removeMetadata(ATTRIBUTE_CACHE_KEY, plugin)
        cache.invalid(entity)
        return computeLivingEntityAttribute(entity)
    }
    fun deleteLivingEntityAttributeCache(entity: LivingEntity) { //Make cache invalid
//        entity.removeMetadata(ATTRIBUTE_CACHE_KEY, plugin)
        cache.invalid(entity)
    }

    fun handleEventData(attrMap: AttributeMap, eventData: EventData) {
        subAttributeRegistry.forEachMap(attrMap) { attrType, subAttribute, attrValue ->
            if(attrValue != null) subAttribute.calculateEventNumber(attrValue, attrMap, eventData)
        }
    }
    fun reloadAttributes() {
        attributeCalculatorRegistry.pipeLineView.forEach { calculator ->
            calculator.onReload()
        }
        subAttributeRegistry.pipeLineView.forEach { subAttribute ->
            subAttribute.onReload()
        }
    }
//    fun calcValue(attrType: AttributeType, attrMap: Map<String, Double>, baseValue: Double): Double {
//        val valueCalculator = valueCalculatorMap[attrType] ?: return baseValue
//        return valueCalculator.calcValue(attrMap, baseValue)
//    }

}