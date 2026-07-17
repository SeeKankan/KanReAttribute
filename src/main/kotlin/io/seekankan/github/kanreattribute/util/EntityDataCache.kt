package io.seekankan.github.kanreattribute.util

/*
@Deprecated("listener太不好了，应该使用TransientEntityDataCache")
class EntityDataCache<T: Entity, E: Any> {
    private class CacheListener( //只是用来清理残留的Data
        private val cache: EntityDataCache<out Entity, out Any>
    ): Listener {
        private fun invalidIfNotPlayer(entity: Entity) {
            if(entity !is Player) cache.invalid(entity.uniqueId)
        }

        @EventHandler
        fun onEntityDeath(event: EntityDeathEvent) { //possible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onEntityDespawn(event: ItemDespawnEvent) { //impossible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onEntityDrop(event: EntityDropItemEvent) { //possible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onEntityEnterBlock(event: EntityEnterBlockEvent) { //impossible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onEntityChangeBlock(event: EntityChangeBlockEvent) { //maybe impossible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onEntityExplode(event: EntityExplodeEvent) { //impossible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onProjectileHit(event: ProjectileHitEvent) { //impossible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onItemMerge(event: ItemMergeEvent) { //impossible for player
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        //out of world function require to write
        @EventHandler
        fun onItemPickUp(event: EntityPickupItemEvent) { //item isn't player
            val item = event.item
            invalidIfNotPlayer(item)
        }
        @EventHandler
        fun onPlayerQuit(event: PlayerQuitEvent) { //must be player
            val entity = event.player
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onEntityTransform(event: EntityTransformEvent) {
            val entity = event.entity
            invalidIfNotPlayer(entity)
        }
        @EventHandler
        fun onChunkUnload(event: ChunkUnloadEvent) {
            val entities = event.chunk.entities
            for (entity in entities) {
                invalidIfNotPlayer(entity)
            }
        }
    }
    private val cacheMap = ConcurrentHashMap<UUID, E>()
    private var cacheListener: CacheListener? = null

    operator fun get(entity: T): E? {
        return this[entity.uniqueId]
    }
    operator fun get(entityID: UUID): E? {
        return cacheMap[entityID]
    }
    operator fun set(entity: T, data: E) {
        this[entity.uniqueId] = data
    }
    operator fun set(entityID: UUID, data: E) {
        cacheMap[entityID] = data
    }
    fun getOrCompute(entity: T, defaultValue: (T) -> E): E {
        return this.getOrCompute(entity.uniqueId) { defaultValue(entity) }
    }
    fun getOrCompute(entityID: UUID, defaultValue: (UUID) -> E): E {
        return cacheMap.computeIfAbsent(entityID) { defaultValue(it) }
    }
    fun invalid(entity: T) {
        this.invalid(entity.uniqueId)
    }
    fun invalid(entityID: UUID) {
        cacheMap.remove(entityID)
    }

    fun registerListener(plugin: Plugin) {
        if(cacheListener != null) return //ignore re-register the listener
        val listener = CacheListener(this)
        cacheListener = listener
        plugin.server.pluginManager.registerEvents(listener, plugin)
    }
    fun unregisterListener() {
        cacheListener?.let { listener ->
            HandlerList.unregisterAll(listener)
        }
    }
}
 */