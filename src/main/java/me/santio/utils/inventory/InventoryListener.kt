package me.santio.utils.inventory

import me.santio.utils.SantioUtils
import me.santio.utils.bukkit.AsyncUtils
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
        val inventory = CustomInventory.getOpenInventory(event.player as Player) ?: return

        if (SantioUtils.switching.contains(event.player.uniqueId)) {
            SantioUtils.switching.remove(event.player.uniqueId)
            return
        }

        inventory.close(event.player as Player)
    }

    @EventHandler
    private fun onInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) return
        val inventory = CustomInventory.getOpenInventory(event.whoClicked as Player) ?: return

        if (event.clickedInventory != inventory.getBukkitInventory()) return
        inventory.onClick(event)
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        SantioUtils.inventories.forEach { if (it.isOpen(event.player.uniqueId)) it.close(event.player) }
    }

}