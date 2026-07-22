package io.seekankan.github.kanreattribute.listener

import io.seekankan.github.kanreattribute.di.AutoRegistrable
import io.seekankan.github.kanreattribute.util.TransientEntityDataCacheManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRemoveEvent
import org.bukkit.event.player.PlayerQuitEvent

class CacheCleanerListener(
    private val transientEntityDataCacheManager: TransientEntityDataCacheManager
): Listener, AutoRegistrable {

//    @EventHandler
//    fun onEntityRemoveFromWorld(event: EntityRemoveFromWorldEvent) {
//        transientEntityDataCacheManager.forEachCache { cache ->
//            cache.remove(event.entity.uniqueId)
//        }
//    }
    @EventHandler
    fun onEntityRemove(event: EntityRemoveEvent) {
        transientEntityDataCacheManager.forEachCache { cache ->
            cache.remove(event.entity.uniqueId)
        }
    }
    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        transientEntityDataCacheManager.forEachCache { cache ->
            cache.remove(event.player.uniqueId)
        }
    }

}