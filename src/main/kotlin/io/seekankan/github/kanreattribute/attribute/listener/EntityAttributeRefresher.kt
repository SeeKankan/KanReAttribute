package io.seekankan.github.kanreattribute.attribute.listener

import io.seekankan.github.kanreattribute.attribute.AttributeManager
import net.axay.kspigot.event.listen
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.TradeSelectEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class EntityAttributeRefresher(
    private val attributeManager: AttributeManager,
): Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val entity = event.whoClicked
        attributeManager.deleteLivingEntityAttributeCache(entity)
    }
    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent) {
        val entity = event.whoClicked
        attributeManager.deleteLivingEntityAttributeCache(entity)
    }
//    @EventHandler
//    fun onInventoryTradeSelect(event: TradeSelectEvent) {
//        val entity = event.whoClicked
//        attributeManager.deleteLivingEntityAttributeCache(entity)
//    }
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val entity = event.player
        if(event.action == Action.RIGHT_CLICK_BLOCK || event.action == Action.RIGHT_CLICK_AIR) {
            attributeManager.deleteLivingEntityAttributeCache(entity)
        }
    }
    @EventHandler
    fun onPlayerDropItem(event: PlayerDropItemEvent) {
        val entity = event.player
        attributeManager.deleteLivingEntityAttributeCache(entity)
    }
    @EventHandler
    fun onPlayerHeldItem(event: PlayerItemHeldEvent) {
        val entity = event.player
        attributeManager.deleteLivingEntityAttributeCache(entity)
    }
    @EventHandler
    fun onPlayerSwapItem(event: PlayerSwapHandItemsEvent) {
        val entity = event.player
        attributeManager.deleteLivingEntityAttributeCache(entity)
    }
    @EventHandler
    fun onPlayerBrokeItem(event: PlayerItemBreakEvent) {
        val entity = event.player
        attributeManager.deleteLivingEntityAttributeCache(entity)
    }
    @EventHandler
    fun onPlayerConsumeItem(event: PlayerItemConsumeEvent) {
        val entity = event.player
        attributeManager.deleteLivingEntityAttributeCache(entity)
    }
}