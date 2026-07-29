package io.seekankan.github.kanreattribute.attribute

import io.seekankan.github.kanreattribute.KanReAttribute
import io.seekankan.github.kanreattribute.attribute.data.AttributeMap
import io.seekankan.github.kanreattribute.attribute.data.AttributeView
import io.seekankan.github.kanreattribute.coroutines.BukkitDispatcher
import io.seekankan.github.kanreattribute.coroutines.annotation.LaunchesCoroutine
import io.seekankan.github.kanreattribute.registry.impl.AttributeCalculatorRegistry
import io.seekankan.github.kanreattribute.registry.impl.EffectApplierRegistry
import io.seekankan.github.kanreattribute.registry.impl.SubAttributeRegistry
import org.bukkit.entity.LivingEntity

class AttributeManager(
    private val plugin: KanReAttribute,
    private val bukkitDispatcher: BukkitDispatcher,

    private val attributeRefreshDebounceHandle: AttributeRefreshDebounceHandle,

    private val attributeCalculatorRegistry: AttributeCalculatorRegistry,
    private val subAttributeRegistry: SubAttributeRegistry,
    private val effectApplierRegistry: EffectApplierRegistry,

    private val attributeCache: LivingEntityAttributeCache
) {

//    private val cache = EntityDataCache<LivingEntity, AttributeMap>()

    fun registerListener() {
//        cache.registerListener(plugin)
    }
    fun unregisterListener() {
//        cache.unregisterListener()
    }

    fun computeLivingEntityAttribute(entity: LivingEntity): AttributeView {
        val entityAttributeMap = AttributeMap()
        attributeCalculatorRegistry.snapshot.pipeline.forEach { calculator ->
            calculator.calculate(entity, entityAttributeMap)
        }
        return entityAttributeMap
    }
    fun triggerAttributeUpdate(entity: LivingEntity, attributeMap: AttributeView) {
        subAttributeRegistry.snapshot.pipeline.forEach { subAttribute ->
            subAttribute.onUpdate(entity, attributeMap.getOrDefault(subAttribute.uniqueName, 0.0), attributeMap)
        }
    }
    fun getLivingEntityAttribute(entity: LivingEntity): AttributeView {
        return attributeCache.computeIfAbsent(entity) {
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
    fun refreshLivingEntityAttribute(entity: LivingEntity): AttributeView {
        val newAttribute = computeLivingEntityAttribute(entity)
        attributeCache[entity] = newAttribute
        triggerAttributeUpdate(entity, newAttribute)
        return newAttribute
    }
    fun deleteLivingEntityAttributeCache(entity: LivingEntity) { //Make cache invalid
        attributeCache.remove(entity)
    }

//    fun reloadAttributes() {
//        attributeCalculatorRegistry.snapshot.pipeline.forEach { calculator ->
//            calculator.onReload()
//        }
//        subAttributeRegistry.snapshot.pipeline.forEach { subAttribute ->
//            subAttribute.onReload()
//        }
//        effectApplierRegistry.reloadAll()
//    }

}