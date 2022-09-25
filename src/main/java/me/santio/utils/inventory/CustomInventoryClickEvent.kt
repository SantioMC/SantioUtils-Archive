@file:Suppress("unused")

package me.santio.utils.inventory

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class CustomInventoryClickEvent(
    private val event: InventoryClickEvent,
    private val prevInventory: CustomInventory
): InventoryClickEvent(
    event.view,
    event.slotType,
    event.slot,
    event.click,
    event.action
) {

    fun getCustomInventory(): CustomInventory {
        return prevInventory
    }

    fun getPaginatedInventory(): PaginatedInventory {
        return prevInventory as PaginatedInventory
    }

    fun forward(inventory: CustomInventory) {
        if (!prevInventory.deleteOnClose() || !inventory.deleteOnClose()) {
            throw IllegalStateException("Cannot forward to a custom inventory that doesn't delete on close")
        }

        prevInventory.next = inventory
        inventory.previous = prevInventory

        inventory.open(event.whoClicked as Player)
    }

    fun back() {
        if (prevInventory.previous == null) {
            event.whoClicked.closeInventory()
            return
        }

        if (prevInventory.previous is PaginatedInventory) {
            val paginatedInventory = prevInventory.previous as PaginatedInventory
            paginatedInventory.open(event.whoClicked as Player, paginatedInventory.page(event.whoClicked.uniqueId))
        } else {
            prevInventory.previous!!.open(event.whoClicked as Player)
        }

        prevInventory.previous!!.next = null

        prevInventory.previous = null
        prevInventory.delete()
    }

}