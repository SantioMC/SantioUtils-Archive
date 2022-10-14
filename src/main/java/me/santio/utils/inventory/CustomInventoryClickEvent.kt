@file:Suppress("unused")

package me.santio.utils.inventory

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.function.Consumer

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

        if (inventory is PaginatedInventory) inventory.open(event.whoClicked as Player, 1)
        else inventory.open(event.whoClicked as Player)
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

        Timer().schedule(object : TimerTask() {
            override fun run() { prevInventory.delete() }
        }, 100)
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun setCursor(stack: ItemStack?) {
        this.event.cursor = stack
    }

    override fun getCursor(): ItemStack? = this.event.cursor

    override fun setCurrentItem(stack: ItemStack?) {
        this.event.currentItem = stack
    }

    override fun getCurrentItem(): ItemStack? = this.event.currentItem

    override fun setCancelled(toCancel: Boolean) {
        this.event.isCancelled = toCancel
    }

    override fun isCancelled(): Boolean = this.event.isCancelled

    override fun setResult(newResult: Result) {
        this.event.result = newResult
    }

    override fun getResult(): Result = this.event.result

}