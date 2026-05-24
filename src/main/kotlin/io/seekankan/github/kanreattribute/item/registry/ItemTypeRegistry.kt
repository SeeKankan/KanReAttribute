package io.seekankan.github.kanreattribute.item.registry

import io.seekankan.github.kanreattribute.common.ItemTypeKey
import io.seekankan.github.kanreattribute.item.itemtype.ItemType
import io.seekankan.github.kanreattribute.registry.RegisterResult
import io.seekankan.github.kanreattribute.registry.UnregisterResult
import java.util.Collections
import java.util.NavigableMap
import java.util.TreeSet
import java.util.logging.Logger

open class ItemTypeRegistry(
    private val logger: Logger
) {
    private val items: MutableSet<ItemType> = TreeSet()
    private val persistentRegisterMap: MutableMap<ItemTypeKey, ItemType> = hashMapOf()
    private val transientRegisterMap: MutableMap<ItemTypeKey, ItemType> = hashMapOf()

    val itemSetView: Set<ItemType> by lazy {
        Collections.unmodifiableSet(items)
    }

    fun forEach(action: (ItemType) -> Unit) {
        items.forEach { value ->
            action(value)
        }
    }

    private fun register0(value: ItemType, isPersistent: Boolean): RegisterResult {
        val typeStr = if(isPersistent) "Persistent" else "Transient"
        val regMap = if(isPersistent) persistentRegisterMap else transientRegisterMap
        logger.info("Register $typeStr ItemType >>> [${value.uniqueName}]!")
        if(value in items) {
            logger.warning("Same ItemType Id！An ItemType(${value.uniqueName}) was duplicate.")
            return RegisterResult.Failure.Duplicate(value.uniqueName.toString())
        }
        regMap[value.uniqueName] = value
        items.add(value)
        return RegisterResult.Success
    }
    fun registerPersistent(value: ItemType): RegisterResult {
        return register0(value,true)
    }

    fun registerTransient(value: ItemType): RegisterResult {
        return register0(value,false)
    }
    fun unregister(value: ItemType): UnregisterResult {
        logger.info("Unregister ItemType >>> [${value.uniqueName}]!")
        if(value !in items) {
            logger.warning("The ItemType ${value.uniqueName} doesn't exist in registry!")
            return UnregisterResult.Failure.NotFound(value.uniqueName.toString())
        }
        persistentRegisterMap.remove(value.uniqueName, value)
        transientRegisterMap.remove(value.uniqueName, value)
        return UnregisterResult.Success
    }
    fun clearTransient() {
        val transientItemTypes = transientRegisterMap.toMutableMap()
        transientRegisterMap.clear()
        transientItemTypes.forEach { (_, type) ->
            items.remove(type)
        }
    }

    operator fun get(key: ItemTypeKey): ItemType? {
        return persistentRegisterMap[key] ?: transientRegisterMap[key]
    }
}