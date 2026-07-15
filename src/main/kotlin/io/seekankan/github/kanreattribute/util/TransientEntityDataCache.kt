package io.seekankan.github.kanreattribute.util

import org.bukkit.entity.Entity
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

abstract class TransientEntityDataCache<T: Entity, E: Any>: ConcurrentMap<UUID, E> by ConcurrentHashMap() {

}