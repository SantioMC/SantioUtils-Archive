package me.santio.utils.inventory

import me.santio.utils.SantioUtils
import me.santio.utils.bukkit.AsyncUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.metadata.FixedMetadataValue

object InventoryListener: Listener {

    @EventHandler
    private fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.player !is Player) return
        val inventory = CustomInventory.getOpenInventory(event.player as Player) ?: return
        event.player.removeMetadata("inventory", SantioUtils.plugin!!)

        if (inventory.deleteOnClose() && inventory.isEmpty()) inventory.delete()
    }

    @EventHandler
    private fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.player !is Player) return
        val inventory = CustomInventory.get(event.inventory) ?: return

        event.player.setMetadata("inventory", FixedMetadataValue(SantioUtils.plugin!!, inventory.id))
    }

    @EventHandler
    private fun onInventoryClick(event: InventoryClickEvent) {
        if (event.whoClicked !is Player) return
        val inventory = CustomInventory.get(event.inventory) ?: return

        if (!inventory.isOpen(event.whoClicked as Player)) {
            event.whoClicked.setMetadata("inventory", FixedMetadataValue(SantioUtils.plugin!!, inventory.id))
        }

        if (event.clickedInventory != inventory.getBukkitInventory()) return
        inventory.onClick(event)
    }

    @EventHandler
    private fun onQuit(event: PlayerQuitEvent) {
        SantioUtils.inventories.forEach { if (it.isOpen(event.player)) it.close(event.player) }
    }

}