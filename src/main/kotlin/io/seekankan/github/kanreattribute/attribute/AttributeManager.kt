package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.coroutines.BukkitDispatcher
import io.seekankan.github.kanreattribute.coroutines.CoroutineManager
import io.seekankan.github.kanreattribute.coroutines.annotation.LaunchesCoroutine
import io.seekankan.github.kanreattribute.util.EntityDataCache
import org.bukkit.entity.LivingEntity
import org.bukkit.event.HandlerList

class AttributeManager constructor(
    private val plugin: KanReAttribute,
    private val bukkitDispatcher: BukkitDispatcher,

    private val attributeRefreshDebounceHandle: AttributeRefreshDebounceHandle,

    private val attributeCalculatorRegistry: AttributeCalculatorRegistry,
    private val subAttributeRegistry: SubAttributeRegistry,
    private val effectApplierRegistry: EffectApplierRegistry,
) {

    private val cache = EntityDataCache<LivingEntity, AttributeMap>()

//    val attributeCalculatorRegistry = AttributeCalculatorRegistry(plugin)



//    val livingEntityAttributeCache = hashMapOf<UUID, AttributeMap>()
    fun registerListener() {
//        cacheListener = EntityDataCache.CacheListener(cache)
//        plugin.server.pluginManager.registerEvents(cacheListener, plugin)
        cache.registerListener(plugin)
    }
    fun unregisterListener() {
//        if(::cacheListener.isInitialized) {
//            HandlerList.unregisterAll(cacheListener)
//        }
        cache.unregisterListener()
    }

    fun computeLivingEntityAttribute(entity: LivingEntity): AttributeMap {
        val entityAttributeMap = AttributeMap()
        attributeCalculatorRegistry.pipeLineView.forEach { calculator ->
            calculator.calculate(entity, entityAttributeMap)
        }
        return entityAttributeMap
    }
    fun triggerAttributeUpdate(entity: LivingEntity, attributeMap: AttributeMap) {
        subAttributeRegistry.pipeLineView.forEach { subAttribute ->
            subAttribute.onUpdate(entity, attributeMap.getOrDefault(subAttribute.uniqueName, 0.0), attributeMap)
        }
    }
    fun getLivingEntityAttribute(entity: LivingEntity): AttributeMap {
//        if(!entity.hasMetadata(ATTRIBUTE_CACHE_KEY)) {
//            val attrMap = computeLivingEntityAttribute(entity)
//            entity.setMetadata(ATTRIBUTE_CACHE_KEY, FixedMetadataValue(plugin, attrMap))
//            return attrMap
//        }
//        return entity.getMetadata(ATTRIBUTE_CACHE_KEY)[0].value() as AttributeMap
        return cache.getOrCompute(entity) {
            val attrMap = computeLivingEntityAttribute(entity)
            triggerAttributeUpdate(entity, attrMap)
            attrMap
        }

    }
    @LaunchesCoroutine
    fun scheduleRefreshLivingEntityAttribute(entity: LivingEntity) {
        attributeRefreshDebounceHandle.trigger(entity.uniqueId, bukkitDispatcher) {
            refreshLivingEntityAttribute(entity)
        }
    }
    fun refreshLivingEntityAttribute(entity: LivingEntity): AttributeMap {
//        cache.invalid(entity)
//        return computeLivingEntityAttribute(entity)
        val newAttribute = computeLivingEntityAttribute(entity)
        cache[entity] = newAttribute
        triggerAttributeUpdate(entity, newAttribute)
        return newAttribute
    }
    fun deleteLivingEntityAttributeCache(entity: LivingEntity) { //Make cache invalid
//        entity.removeMetadata(ATTRIBUTE_CACHE_KEY, plugin)
        cache.invalid(entity)
    }

    fun reloadAttributes() {
        attributeCalculatorRegistry.pipeLineView.forEach { calculator -> //TODO 该改正了
            calculator.onReload()
        }
        subAttributeRegistry.pipeLineView.forEach { subAttribute ->
            subAttribute.onReload()
        }
        effectApplierRegistry.reloadAndClearTransient()
    }
//    fun calcValue(attrType: AttributeType, attrMap: Map<String, Double>, baseValue: Double): Double {
//        val valueCalculator = valueCalculatorMap[attrType] ?: return baseValue
//        return valueCalculator.calcValue(attrMap, baseValue)
//    }

}