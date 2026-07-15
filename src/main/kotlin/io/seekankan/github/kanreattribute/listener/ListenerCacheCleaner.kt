package io.seekankan.github.kanreattribute.listener

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent
import io.seekankan.github.kanreattribute.di.AutoRegistrable
import io.seekankan.github.kanreattribute.util.TransientEntityDataCacheManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRemoveEvent

class ListenerCacheCleaner(
    private val transientEntityDataCacheManager: TransientEntityDataCacheManager
): Listener, AutoRegistrable {

    @EventHandler
    fun onEntityRemoveFromWorld(event: EntityRemoveFromWorldEvent) {
        transientEntityDataCacheManager.forEachCache { cache ->
            cache.remove(event.entity.uniqueId)
        }
    }
    @EventHandler
    fun onEntityRemove(event: EntityRemoveEvent) {
        transientEntityDataCacheManager.forEachCache { cache ->
            cache.remove(event.entity.uniqueId)
        }
    }

}