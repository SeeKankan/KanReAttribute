package io.seekankan.github.kanreattribute.util

import org.bukkit.entity.Entity
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class TransientEntityDataCacheManager {
    private val dataCaches: MutableSet<TransientEntityDataCache<*, *>> = Collections.newSetFromMap(ConcurrentHashMap())

    fun <T : Entity, E : Any> register(dataCache: TransientEntityDataCache<T, E>) {
        dataCaches.add(dataCache)
    }
    fun registerAll(dataCacheList: List<TransientEntityDataCache<*, *>>) {
        dataCaches.addAll(dataCacheList)
    }

    fun <T : Entity, E : Any> unregister(dataCache: TransientEntityDataCache<T, E>) {
         dataCaches.remove(dataCache)
    }
    fun forEachCache(action: (TransientEntityDataCache<*, *>) -> Unit) {
        dataCaches.forEach(action)
    }
}