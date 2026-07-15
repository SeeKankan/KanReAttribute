package io.seekankan.github.kanreattribute.listener

import io.papermc.paper.event.player.PrePlayerAttackEntityEvent
import io.seekankan.github.kanreattribute.di.AutoRegistrable
import io.seekankan.github.kanreattribute.helper.PlayerPreAttackCooldownCache
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class ListenerCachePlayerAttackCooldown(
    private val playerPreAttackCooldownCache: PlayerPreAttackCooldownCache
): Listener, AutoRegistrable {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onPlayerPreAttack(event: PrePlayerAttackEntityEvent) {
        val strength = event.player.getCooledAttackStrength(0.0f)
        playerPreAttackCooldownCache.put(event.player.uniqueId, strength)
    }

}