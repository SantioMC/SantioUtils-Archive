package me.santio.utils.inventory

import me.santio.utils.SantioUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerQuitEvent

object InventoryListener: Listener {

    @EventHandler
    private fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.player !is Player) return

        SantioUtils.inventories.forEach { if (it.isOpen(event.player.uniqueId)) it.close(event.player as Player) }
    }

    @EventHandler
    private fun onInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) return
        SantioUtils.inventories.forEach { if (it.isOpen(event.whoClicked.uniqueId)) it.onClick(event) }
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        SantioUtils.inventories.forEach { if (it.isOpen(event.player.uniqueId)) it.close(event.player) }
    }

}