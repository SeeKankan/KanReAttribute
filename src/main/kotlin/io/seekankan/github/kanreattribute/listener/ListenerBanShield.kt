package io.seekankan.github.kanreattribute.listener

import io.seekankan.github.kanreattribute.ConfigHolder
import io.seekankan.github.kanreattribute.di.AutoRegistrable
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class ListenerBanShield(
    private val configHolder: ConfigHolder
): Listener, AutoRegistrable {

    @EventHandler
    fun onPlayerClickEvent(event: PlayerInteractEvent) {
        if (event.useInteractedBlock() != Event.Result.DENY) {
            val config = configHolder.currentConfig
            event.setCancelled(config.combat.banShield && event.item != null && event.item!!.type == Material.SHIELD);
        }
    }

}