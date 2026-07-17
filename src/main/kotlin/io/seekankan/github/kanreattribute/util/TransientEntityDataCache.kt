package io.seekankan.github.kanreattribute.util

import org.bukkit.entity.Entity
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

abstract class TransientEntityDataCache<T: Entity, E: Any>: ConcurrentMap<UUID, E> by ConcurrentHashMap() {

    operator fun get(entity: T): E? {
        return this[entity.uniqueId]
    }
    operator fun set(entity: T, value: E) {
        this[entity.uniqueId] = value
    }

    fun remove(entity: T): E? {
        return this.remove(entity.uniqueId)
    }

    fun computeIfAbsent(entity: T, mappingFunction: (T) -> E): E {
        return this.computeIfAbsent(entity.uniqueId) { mappingFunction(entity) }
    }

}