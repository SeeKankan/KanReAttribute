package io.seekankan.github.kanreattribute.listener

import io.seekankan.github.kanreattribute.Config
import io.seekankan.github.kanreattribute.di.AutoRegistrable
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class ListenerBanShield(
    private val config: Config
): Listener, AutoRegistrable {

    @EventHandler
    fun onPlayerClickEvent(event: PlayerInteractEvent) {
        if (!event.isCancelled) {
            event.setCancelled(config.banShield && event.item != null && event.item!!.type == Material.SHIELD);
        }
    }

}