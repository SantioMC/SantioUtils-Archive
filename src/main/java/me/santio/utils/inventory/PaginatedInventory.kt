package me.santio.utils.inventory

import me.santio.utils.item.CustomItem
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.function.BiConsumer
import java.util.function.Consumer
import kotlin.math.ceil

@Suppress("MemberVisibilityCanBePrivate")
class PaginatedInventory(
    inv: CustomInventory
): CustomInventory(inv.rows(), inv.name) {

    init { import(inv.export()) }

    private var slots: Slots = Slots.ALL
    private var items: MutableList<ItemStack> = mutableListOf()
    private var events: MutableMap<Int, MutableMap<Int, Consumer<InventoryClickEvent>>> = mutableMapOf()
    private var switchConsumers: MutableList<BiConsumer<PaginatedInventory, Int>> = mutableListOf()

    private var back: Pair<Slots, ItemStack>? = null
    private var forward: Pair<Slots, ItemStack>? = null

    @JvmOverloads
    fun open(player: Player, page: Int = 1): PaginatedInventory {
        if (isOpen(player)) switchConsumers.forEach { it.accept(this, page) }

        player.openInventory(this.inventory)
        this.opened.add(player.uniqueId)

        // Paginate items
        val paginatedItems = items.subList((page - 1) * slots.size(), (page * slots.size()).coerceAtMost(items.size))
        paginatedItems.forEachIndexed { index, item -> inventory.setItem(slots.get(index), item) }

        // Add back and forward buttons
        if (page > 1) back?.let { set(it.first, it.second) { e ->
            e.isCancelled = true
            open(e.whoClicked as Player, page - 1)
        }} else back?.let { set(it.first, CustomItem.fromItem(it.second).name("&cFirst Page")) }

        if (page < pages()) forward?.let { set(it.first, it.second) { e ->
            e.isCancelled = true
            open(e.whoClicked as Player, page + 1)
        }} else forward?.let { set(it.first, CustomItem.fromItem(it.second).name("&cLast Page")) }

        return this
    }

    fun pages() = ceil(items.size / slots.size().toDouble()).toInt()

    fun useSlots(slots: Slots): PaginatedInventory {
        this.slots = slots
        return this
    }

    fun addItems(vararg items: ItemStack): PaginatedInventory {
        this.items.addAll(items)
        return this
    }

    fun setEvent(page: Int, slot: Int, event: Consumer<InventoryClickEvent>): PaginatedInventory {
        events.getOrPut(page) { mutableMapOf() }[slot] = event
        return this
    }

    fun addBackButton(slots: Slots, item: ItemStack): PaginatedInventory {
        back = Pair(slots, item)
        return this
    }

    fun addForwardButton(slots: Slots, item: ItemStack): PaginatedInventory {
        forward = Pair(slots, item)
        return this
    }

    fun addBackButton(slot: Int, item: ItemStack): PaginatedInventory {
        return addBackButton(Slots.get(slot), item)
    }

    fun addForwardButton(slot: Int, item: ItemStack): PaginatedInventory {
        return addForwardButton(Slots.get(slot), item)
    }

    fun onPageChange(page: BiConsumer<PaginatedInventory, Int>): PaginatedInventory {
        switchConsumers.add(page)
        return this
    }

}