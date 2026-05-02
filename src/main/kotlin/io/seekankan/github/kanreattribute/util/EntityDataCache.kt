package io.seekankan.github.kanreattribute.util

import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.EntityDropItemEvent
import org.bukkit.event.entity.EntityEnterBlockEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.event.entity.EntityTransformEvent
import org.bukkit.event.entity.ItemDespawnEvent
import org.bukkit.event.entity.ItemMergeEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.ChunkUnloadEvent
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class EntityDataCache<T: Entity, E: Any> {
    class CacheListener(
        private val cache: EntityDataCache<out Entity, out Any>
    ): Listener {

        @EventHandler
        fun onEntityDeath(event: EntityDeathEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onEntityDespawn(event: ItemDespawnEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onEntityDrop(event: EntityDropItemEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onEntityEnterBlock(event: EntityEnterBlockEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onEntityChangeBlock(event: EntityChangeBlockEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onEntityExplode(event: EntityExplodeEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onProjectileHit(event: ProjectileHitEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onItemMerge(event: ItemMergeEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        //out of world function require to write
        @EventHandler
        fun onItemPickUp(event: EntityPickupItemEvent) {
            val entity = event.item
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onPlayerQuit(event: PlayerQuitEvent) {
            val entity = event.player
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onEntityTransform(event: EntityTransformEvent) {
            val entity = event.entity
            cache.invalid(entity.uniqueId)
        }
        @EventHandler
        fun onChunkUnload(event: ChunkUnloadEvent) {
            val entities = event.chunk.entities
            for (entity in entities) {
                cache.invalid(entity.uniqueId)
            }
        }
    }
    private val cacheMap = ConcurrentHashMap<UUID, E>()

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
}